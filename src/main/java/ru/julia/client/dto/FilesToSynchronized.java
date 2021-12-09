package ru.julia.client.dto;

import lombok.Data;

import java.util.List;
@Data
public class FilesToSynchronized {
    List<String> filesToTransfer;
    List<String> filesToReceive;
    List<String> filesToDeleteFromServer;
    List<String> filesToDeleteFromClient;

    public FilesToSynchronized(List<String> filesToTransfer, List<String> filesToReceive,
                               List<String> filesToDeleteFromServer, List<String> filesToDeleteFromClient) {
        this.filesToTransfer = filesToTransfer;
        this.filesToReceive = filesToReceive;
        this.filesToDeleteFromServer = filesToDeleteFromServer;
        this.filesToDeleteFromClient = filesToDeleteFromClient;
    }

    public FilesToSynchronized() {

    }


}
