package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

public interface CommandHandler {
    void handle(Command command);
}
