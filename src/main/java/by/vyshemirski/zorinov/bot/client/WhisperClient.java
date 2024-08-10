package by.vyshemirski.zorinov.bot.client;

import by.vyshemirski.zorinov.bot.model.WhisperTranscriptionRequest;
import by.vyshemirski.zorinov.bot.model.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class WhisperClient {

    @Value("${open-ai.whisper.model}")
    private String modelName;

    private final RestClient restClient;


    @SneakyThrows
    public WhisperTranscriptionResponse fetchAudioTranscription(WhisperTranscriptionRequest request) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(request.getAudioFile()));
        body.add("model", modelName);

        ResponseEntity<WhisperTranscriptionResponse> responseEntity = restClient.post()
                .body(body)
                .retrieve()
                .toEntity(WhisperTranscriptionResponse.class);

        return responseEntity.getBody();
    }

}
