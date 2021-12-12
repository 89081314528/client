package ru.julia.client.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.julia.client.clients.FileStorageClient;
import ru.julia.client.dto.FilesToSynchronized;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClientServiceImpl implements ClientService {
    private final FileStorageClient fileStorageClient;
    @Value(value = "${clientName}")
    private String clientName;
    @Value(value = "${pathToClientHomeDir}")
    private String path;
    @Value(value = "${idDevice}")
    private String idDevice;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @Override
    public void answerFromServer() {
        List<String> clientFiles = makeClientFilesList();
        if (fileStorageClient.getSyncLock(clientName + idDevice) == 0) {
            fileStorageClient.makeSyncLock(clientName + idDevice);
            log.info("синхронизация клиента " + clientName + idDevice + " началась");

            FilesToSynchronized filesToSynchronized =
                    fileStorageClient.filesToSynchronized(clientFiles, clientName, idDevice);

            // удаление с клиента (этот метод только у клиента)
            List<String> filesToDeleteFromClient = filesToSynchronized.getFilesToDeleteFromClient();
            for (String s : filesToDeleteFromClient) {
                log.info("удален файл у клиента " + s);
                File file = new File(path + "/" + clientName + "/" + s);
                file.delete();
            }
            // добавление клиенту с сервера
            List<String> filesToTransferToClient = filesToSynchronized.getFilesToTransfer();
            for (String s : filesToTransferToClient) {
                String dirPath = path + "/" + clientName + "/";
                byte[] bytes = fileStorageClient.transferToClient(clientName, s);
                try {
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(dirPath + s));
                    stream.write(bytes);
                    stream.close();
                    log.info("Передан файл " + s);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            // добавление на сервер с клиента
            List<String> filesToReceiveFromClient = filesToSynchronized.getFilesToReceive();
            for (String s : filesToReceiveFromClient) {
                log.info("Получен файл " + s);
                String path1 = path + "/" + clientName + "/" + s;
                File file = new File(path1);
                DiskFileItem fileItem = new DiskFileItem("file", "application/octet-stream",
                        false, file.getName(), (int) file.length(), file.getParentFile());
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.transferTo(fileItem.getOutputStream());
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
                fileStorageClient.receiveFromClient(clientName, s, multipartFile);
            }
            fileStorageClient.getLastSyncDate(clientName + idDevice);
            fileStorageClient.makeSyncOpen(clientName + idDevice);
            log.info("синхронизация клиента " + clientName + idDevice +  " закончилась");
        }
    }

    List<String> makeClientFilesList() {
        List<String> clientFiles = new ArrayList<>();
        String dirPath = path + "/" + clientName;
        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                clientFiles.add(file.getName());
            }
        } else {
            throw new RuntimeException("Client storage directory not found " + path);
        }
        return clientFiles;
    }
}
