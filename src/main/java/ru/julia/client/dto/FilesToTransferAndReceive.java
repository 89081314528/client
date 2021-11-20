package ru.julia.client.dto;

import java.util.List;

public class FilesToTransferAndReceive {
    List<String> filesToTransfer;
    List<String> filesToReceive;

    public FilesToTransferAndReceive(List<String> filesToTransfer, List<String> filesToReceive) {
        this.filesToTransfer = filesToTransfer;
        this.filesToReceive = filesToReceive;
    }

    public FilesToTransferAndReceive() {

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
