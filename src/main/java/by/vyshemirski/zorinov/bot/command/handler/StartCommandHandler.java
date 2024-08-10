package by.vyshemirski.zorinov.bot.command.handler;

import by.vyshemirski.zorinov.bot.command.TelegramCommandHandler;
import by.vyshemirski.zorinov.bot.command.TelegramCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class StartCommandHandler implements TelegramCommandHandler {
    @Override
    public SendMessage processCommand(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format("""
                                Hello %s!👋,
                                Send me any request and I will process it using ChatGPT.
                                Or send a voice/audio message and I'll send you a transcript of it.""",
                        message.getChat().getFirstName())
                ).build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START_COMMAND;
    }
}
