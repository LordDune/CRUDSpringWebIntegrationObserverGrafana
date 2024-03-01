package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel textInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriteChannel() {
        return new DirectChannel();
    }

    // Берет данные из textInputChannel и кладет в fileWriteChannel
    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWriteChannel")
    public GenericTransformer<String, String> transformer() {
         return text -> {
             return text;
         };
    }

    // берет данные из fileWriteChannel и записывает в файл
    @Bean
    @ServiceActivator(inputChannel = "fileWriteChannel")
    public FileWritingMessageHandler messageHandler(){
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(new File("./demo/tmp/user_updates"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
