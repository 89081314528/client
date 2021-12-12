package ru.julia.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesToSynchronized {
    List<String> filesToTransfer;
    List<String> filesToReceive;
    List<String> filesToDeleteFromServer;
    List<String> filesToDeleteFromClient;
    }
