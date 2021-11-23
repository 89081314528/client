package ru.julia.client.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.julia.client.dto.FilesToTransferAndReceive;

import java.util.List;
// аннотация обозначает, что реализацию интерфейса спринг сделает сам и
@FeignClient(name = "localhost", url = "http://localhost:8080/")
public interface FileStorageClient {
    @RequestMapping("/getFiles")
    FilesToTransferAndReceive filesToTransferAndReceive(@RequestParam("filesFromClient") List<String> filesFromClient,
                                                               @RequestParam("clientName")String clientName);

    @RequestMapping("/transfer") // строит запрос для сервера
    String transfer(@RequestParam("clientName") String clientName, @RequestParam("fileName") String fileName);

    @RequestMapping("/receive")
    String receive(@RequestParam("clientName") String clientName, @RequestParam("fileName") String fileName);

}
