package com.example.demo.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

// Данный интерфейс кладет данные в очередь textInputChannel
@MessagingGateway(defaultRequestChannel = "textInputChannel")
public interface FileGateWay {

    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
