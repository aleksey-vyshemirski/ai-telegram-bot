package by.vyshemirski.zorinov.bot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    SendMessage processMessage(Message message);

    Boolean canProcess(Message message);

}
