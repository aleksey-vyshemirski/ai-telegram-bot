package by.vyshemirski.zorinov.bot.handler.impl;

import by.vyshemirski.zorinov.bot.client.WhisperClient;
import by.vyshemirski.zorinov.bot.handler.MessageHandler;
import by.vyshemirski.zorinov.bot.model.WhisperTranscriptionRequest;
import by.vyshemirski.zorinov.bot.model.WhisperTranscriptionResponse;
import by.vyshemirski.zorinov.bot.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Voice;

import java.io.File;

@Component
public class AudioMessageHandler implements MessageHandler {

    private final WhisperClient whisperClient;
    private final FileService fileService;

    @Autowired
    public AudioMessageHandler(WhisperClient whisperClient, FileService fileService) {
        this.whisperClient = whisperClient;
        this.fileService = fileService;
    }

    @Override
    public SendMessage processMessage(Message message) {
        Voice voice = message.getVoice();
        Audio audio = message.getAudio();

        String fileId = voice != null ? voice.getFileId() : audio.getFileId();
        File file = fileService.fetchFileFromTelegram(fileId);

        WhisperTranscriptionResponse transcriptionResponse = whisperClient.fetchAudioTranscription(
                new WhisperTranscriptionRequest(file)
        );

        return new SendMessage(message.getChatId().toString(), transcriptionResponse.getText());
    }

    @Override
    public Boolean canProcess(Message message) {
        return message.hasAudio() || message.hasVoice();
    }
}
