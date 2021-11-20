package ru.julia.client.servicies;

import org.springframework.stereotype.Service;
import ru.julia.client.dto.FilesToTransferAndReceive;

@Service
public interface ClientService {
    void answerFromServer (FilesToTransferAndReceive filesToTransferAndReceive);
}
