package ru.julia.client.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.julia.client.clients.FileStorageClient;
import ru.julia.client.dto.FilesToTransferAndReceive;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final FileStorageClient fileStorageClient;

    @Override
    public void answerFromServer() {
        List<String> clientFiles = new ArrayList<>();
        clientFiles.add("file1");
        String clientName = "julia";
        FilesToTransferAndReceive filesToTransferAndReceive = fileStorageClient.filesToTransferAndReceive(clientFiles, clientName);

        List<String> filesToReceive = filesToTransferAndReceive.getFilesToReceive();
        List<String> filesToTransfer = filesToTransferAndReceive.getFilesToTransfer();

        for (String s : filesToTransfer) {
            fileStorageClient.transfer(clientName, s);
            System.out.println("Передан файл " + s);
        }
        for (String s : filesToReceive) {
            fileStorageClient.receive(clientName, s);
            System.out.println("Получен файл " + s);
        }
    }
}
