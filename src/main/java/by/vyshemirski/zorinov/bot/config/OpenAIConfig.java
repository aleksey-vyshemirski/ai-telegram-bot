package by.vyshemirski.zorinov.bot.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAIConfig {

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String token;

    @Value("${open-ai.whisper.transcription-url}")
    private String whisperTranscriptionUrl;

    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(20);
    }

    @Bean
    RestClient whisperRestClient() {
        return RestClient.builder()
                .baseUrl(whisperTranscriptionUrl)
                .defaultHeaders((httpHeaders) -> {
                    httpHeaders.setBearerAuth(token);
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA);
                }).build();
    }
}
