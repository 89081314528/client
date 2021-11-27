package ru.julia.client.dto;

import java.util.List;

public class FilesToDeleteFromServerAndClient {
    List<String> filesToDeleteFromServer;
    List<String> filesToDeleteFromClient;

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

    public FilesToDeleteFromServerAndClient(List<String> filesToDeleteFromServer, List<String> filesToDeleteFromClient) {
        this.filesToDeleteFromServer = filesToDeleteFromServer;
        this.filesToDeleteFromClient = filesToDeleteFromClient;
    }

    public FilesToDeleteFromServerAndClient(){};
}
