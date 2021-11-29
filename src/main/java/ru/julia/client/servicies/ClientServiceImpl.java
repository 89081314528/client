package ru.julia.client.servicies;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.julia.client.clients.FileStorageClient;
import ru.julia.client.dto.FilesToSynchronized;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final FileStorageClient fileStorageClient;
    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @Override
    public void answerFromServer() {
        List<String> clientFiles = makeClientFilesList();
        String clientName = "julia";

        FilesToSynchronized filesToSynchronized =
                fileStorageClient.filesToSynchronized(clientFiles, clientName);

        // удаление с клиента (этот метод только у клиента)
        List<String> filesToDeleteFromClient = filesToSynchronized.getFilesToDeleteFromClient();
        for (String s : filesToDeleteFromClient) {
            System.out.println("удален файл у клиента " + s);
            File file = new File("C:/Users/julia/Desktop/" + clientName + "/" + s);
            file.delete();
        }
        // добавление клиенту с сервера
        List<String> filesToTransferToClient = filesToSynchronized.getFilesToTransfer();
        for (String s : filesToTransferToClient) {
            fileStorageClient.transferToClient(clientName, s);
            System.out.println("Передан файл " + s);
        }
        // добавление на сервер с клиента
        List<String> filesToReceiveFromClient = filesToSynchronized.getFilesToReceive();
        for (String s : filesToReceiveFromClient) {
            log.info("Получен файл " + s);
            String path = "C:/Users/julia/Desktop/" + clientName + "/" + s;
            File file = new File(path);
            DiskFileItem fileItem = new DiskFileItem("file", "application/octet-stream",
                    false, file.getName(), (int) file.length(), file.getParentFile());
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.transferTo(fileItem.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            fileStorageClient.receiveFromClient(clientName, s, multipartFile);
        }
        fileStorageClient.getLastSyncDate(clientName);
    }

    List<String> makeClientFilesList() {
        List<String> clientFiles = new ArrayList<>();
        String dirPath = "C:/Users/julia/Desktop/julia";
        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                clientFiles.add(file.getName());
            }
        } else {
            throw new RuntimeException("Client storage directory not found");
        }
        return clientFiles;
    }
}
