package ru.julia.client.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.julia.client.dto.FilesToTransferAndReceive;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    @Override
    public void answerFromServer(FilesToTransferAndReceive filesToTransferAndReceive) {
        List<String> filesToReceive = filesToTransferAndReceive.getFilesToReceive();
        List<String> filesToTransfer = filesToTransferAndReceive.getFilesToTransfer();

        for (String s : filesToTransfer) {
            System.out.println("Сервер передал файл " + s);
        }

        for (String s : filesToReceive) {
            System.out.println("Сервер получил файл " + s);
        }
    }
}
