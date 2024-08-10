package by.vyshemirski.zorinov.bot.client;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatGPTClient {
    String chat(String userMessage);
}
