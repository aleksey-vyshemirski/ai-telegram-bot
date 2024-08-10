package by.vyshemirski.zorinov.bot.bot;

import by.vyshemirski.zorinov.bot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botUsername;

    private final MessageService messageService;

    @Autowired
    public TelegramBot(@Value("${telegram.bot.token}") String botToken, @Lazy MessageService messageService) {
        super(botToken);
        this.messageService = messageService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        messageService.processMessage(update.getMessage());
    }
}
