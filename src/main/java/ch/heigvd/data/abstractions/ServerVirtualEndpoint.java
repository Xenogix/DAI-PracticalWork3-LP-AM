package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

import java.io.IOException;

public interface ServerVirtualEndpoint {
    void send(Command clientCommand) throws IOException;
}
