package by.vyshemirski.zorinov.bot.handler.impl;

import by.vyshemirski.zorinov.bot.client.ChatGPTClient;
import by.vyshemirski.zorinov.bot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
@Component
public class TextMessageHandler implements MessageHandler {

    private final ChatGPTClient chatGPTClient;

    @Override
    public SendMessage processMessage(Message message) {
        String chatGPTResponse = chatGPTClient.chat(message.getText());

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(chatGPTResponse)
                .build();
    }

    @Override
    public Boolean canProcess(Message message) {
        return message.hasText();
    }
}
