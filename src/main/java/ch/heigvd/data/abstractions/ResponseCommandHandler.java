package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

public interface ResponseCommandHandler {
    Command handle(Command command);
}
