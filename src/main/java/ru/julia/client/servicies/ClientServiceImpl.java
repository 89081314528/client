package ru.julia.client.servicies;

import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.julia.client.clients.FileStorageClient;
import ru.julia.client.dto.FilesToTransferAndReceive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final FileStorageClient fileStorageClient;

    @Override
    public void answerFromServer() {
        List<String> clientFiles = new ArrayList<>();
        String dirPath = "C:/Users/julia/Desktop/client";
        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                clientFiles.add(file.getName());
            }
        } else {
            throw new RuntimeException("Client storage directory not found");
        }
        String clientName = "julia";
        FilesToTransferAndReceive filesToTransferAndReceive =
                fileStorageClient.filesToTransferAndReceive(clientFiles, clientName);

        List<String> filesToReceiveFromClient = filesToTransferAndReceive.getFilesToReceive();
//        List<String> filesToTransferToClient = filesToTransferAndReceive.getFilesToTransfer();

//        for (String s : filesToTransfer) {
//            fileStorageClient.transfer(clientName, s);
//            System.out.println("Передан файл " + s);
//        }
        for (String s : filesToReceiveFromClient) {
            System.out.println("Получен файл " + s);
            String path = "C:/Users/julia/Desktop/client/" + s;
            File file = new File(path);
            DiskFileItem fileItem = new DiskFileItem("file", "application/octet-stream",
                    false, file.getName(), (int) file.length(), file.getParentFile());
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.transferTo(fileItem.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //        fileItem.getOutputStream();
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            fileStorageClient.receiveFromClient(clientName, s, multipartFile);
        }
    }
}
