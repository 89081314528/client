package ru.julia.client.dto;

import java.util.List;

public class FilesToTransferReceiveDelete {
    List<String> filesToTransfer;
    List<String> filesToReceive;
    List<String> filesToDeleteFromServer;

    public List<String> getFilesToDeleteFromServer() {
        return filesToDeleteFromServer;
    }

    public void setFilesToDeleteFromServer(List<String> filesToDeleteFromServer) {
        this.filesToDeleteFromServer = filesToDeleteFromServer;
    }

    public List<String> getFilesToDeleteFromClient() {
        return filesToDeleteFromClient;
    }

    public void setFilesToDeleteFromClient(List<String> filesToDeleteFromClient) {
        this.filesToDeleteFromClient = filesToDeleteFromClient;
    }

    List<String> filesToDeleteFromClient;

    public FilesToTransferReceiveDelete(List<String> filesToTransfer, List<String> filesToReceive,
                                        List<String> filesToDeleteFromServer, List<String> filesToDeleteFromClient) {
        this.filesToTransfer = filesToTransfer;
        this.filesToReceive = filesToReceive;
        this.filesToDeleteFromServer = filesToDeleteFromServer;
        this.filesToDeleteFromClient = filesToDeleteFromClient;
    }

    public FilesToTransferReceiveDelete() {

    }

    public List<String> getFilesToTransfer() {
        return filesToTransfer;
    }

    public void setFilesToTransfer(List<String> filesToTransfer) {
        this.filesToTransfer = filesToTransfer;
    }

    public List<String> getFilesToReceive() {
        return filesToReceive;
    }

    public void setFilesToReceive(List<String> filesToReceive) {
        this.filesToReceive = filesToReceive;
    }

}
