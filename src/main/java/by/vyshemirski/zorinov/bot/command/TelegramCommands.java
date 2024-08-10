package by.vyshemirski.zorinov.bot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TelegramCommands {
    START_COMMAND("/start");

    private final String commandValue;
}
