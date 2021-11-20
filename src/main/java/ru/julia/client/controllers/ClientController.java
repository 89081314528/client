package ru.julia.client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.julia.client.dto.FilesToTransferAndReceive;
import ru.julia.client.servicies.ClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/answerFromServer")
    public void answerFromServer (FilesToTransferAndReceive filesToTransferAndReceive) {
        clientService.answerFromServer(filesToTransferAndReceive);
    }
}
