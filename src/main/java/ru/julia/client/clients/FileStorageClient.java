package ru.julia.client.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.julia.client.dto.FilesToSynchronized;

import java.util.List;

@FeignClient(name = "localhost", url = "http://localhost:8080/", configuration = FeignSupportConfig.class)
public interface FileStorageClient {
    @RequestMapping("/getFiles")
    FilesToSynchronized filesToSynchronized(@RequestParam("filesFromClient") List<String> filesFromClient,

                                            @RequestParam("clientName") String clientName);

    @RequestMapping("/transferToClient")
        // строит запрос для сервера
    String transferToClient(@RequestParam("clientName") String clientName,
                            @RequestParam("fileName") String fileName);

    @PostMapping(value = "/receiveFromClient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String receiveFromClient(@RequestParam("clientName") String clientName,
                             @RequestParam("fileName") String fileName,
                             @RequestPart(value = "file") MultipartFile file);

    @RequestMapping("/deleteFromServer")
    String deleteFromServer(@RequestParam("clientName") String clientName,
                            @RequestParam("fileName") String fileName);

    @RequestMapping("/getLastSyncDate")
    void getLastSyncDate(@RequestParam("clientName") String clientName);

}
