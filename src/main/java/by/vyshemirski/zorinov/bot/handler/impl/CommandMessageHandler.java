package by.vyshemirski.zorinov.bot.handler.impl;

import by.vyshemirski.zorinov.bot.command.TelegramCommandHandler;
import by.vyshemirski.zorinov.bot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 1)
public class CommandMessageHandler implements MessageHandler {

    private final List<TelegramCommandHandler> commandHandlers;

    @Override
    public SendMessage processMessage(Message message) {
        for (TelegramCommandHandler commandHandler : commandHandlers) {
            if (commandHandler.getSupportedCommand().getCommandValue().equals(message.getText())) {
                return commandHandler.processCommand(message);
            }
        }

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Sorry, this command is not supported.")
                .build();
    }

    @Override
    public Boolean canProcess(Message message) {
        return message.isCommand();
    }
}
