package by.vyshemirski.zorinov.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${telegram.bot.token}")
    private String botToken;

    private final DefaultAbsSender telegramSender;

    @SneakyThrows
    public java.io.File fetchFileFromTelegram(String fileId) {
        File file = telegramSender.execute(GetFile.builder()
                .fileId(fileId)
                .build());
        String urlToDownloadFile = file.getFileUrl(botToken);

        return downloadTelegramFile(urlToDownloadFile);
    }

    @SneakyThrows
    private java.io.File downloadTelegramFile(String urlToDownloadFile) {
        URL url = new URI(urlToDownloadFile).toURL();
        var fileTemp = java.io.File.createTempFile("telegram", ".mp4");

        try (InputStream inputStream = url.openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(fileTemp)
        ) {
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            log.error("Error while file copying from url to temp file", e);
            throw new RuntimeException("Error while downloading file", e);
        }
        return fileTemp;
    }
}