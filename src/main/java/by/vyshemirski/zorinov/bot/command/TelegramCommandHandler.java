package by.vyshemirski.zorinov.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramCommandHandler {

    SendMessage processCommand(Message update);
    TelegramCommands getSupportedCommand();

}
