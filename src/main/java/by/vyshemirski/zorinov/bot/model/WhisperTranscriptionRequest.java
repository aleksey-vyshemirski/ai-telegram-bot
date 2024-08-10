package by.vyshemirski.zorinov.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhisperTranscriptionRequest {
    private File audioFile;
}
