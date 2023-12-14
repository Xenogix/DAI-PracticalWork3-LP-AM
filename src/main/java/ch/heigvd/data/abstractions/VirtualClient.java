package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

import java.io.IOException;

public interface VirtualClient {
    Command send(Command clientCommand) throws IOException;
}
