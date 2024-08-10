package by.vyshemirski.zorinov.bot.service;

import by.vyshemirski.zorinov.bot.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private final DefaultAbsSender telegramSender;
    private final List<MessageHandler> messageHandlers;

    public void processMessage(Message message) {
        Optional<MessageHandler> handlerOptional = messageHandlers.stream()
                .filter(handler -> handler.canProcess(message))
                .findFirst();


        if (handlerOptional.isEmpty()) {
            sendMessageSync(
                    SendMessage.builder()
                            .chatId(message.getChatId())
                            .text("Sorry, this type of message is not supported.")
                            .build()
            );
        } else {
            sendMessageAsync(message.getChatId().toString(), () -> handlerOptional.get().processMessage(message));
        }
    }

    @SneakyThrows
    private void sendMessageAsync(
            String chatId,
            Supplier<SendMessage> action
    ) {
        var message = telegramSender.execute(SendMessage.builder()
                .text("Your request has been accepted for processing, please wait...")
                .chatId(chatId)
                .build());

        CompletableFuture.supplyAsync(action, executorService)
                .thenAccept(sendMessage -> {
                    try {
                        telegramSender.execute(EditMessageText.builder()
                                .chatId(chatId)
                                .messageId(message.getMessageId())
                                .text(sendMessage.getText())
                                .build());
                    } catch (TelegramApiException e) {
                        log.error("Error while send request to telegram", e);
                        throw new RuntimeException(e);
                    }
                });
    }

    private void sendMessageSync(SendMessage sendMessage) {
        try {
            telegramSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Cannot send message", e);
        }
    }

}
