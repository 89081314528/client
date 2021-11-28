package ru.julia.client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.julia.client.dto.FilesToTransferReceiveDelete;
import ru.julia.client.servicies.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @RequestMapping("/answerFromServer")
    public void answerFromServer(FilesToTransferReceiveDelete filesToTransferReceiveDelete) {
        clientService.answerFromServer();
    }
}
