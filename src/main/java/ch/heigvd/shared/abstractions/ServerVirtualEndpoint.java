package ch.heigvd.shared.abstractions;

import ch.heigvd.server.commands.ServerCommand;

import java.io.IOException;

public interface ServerVirtualEndpoint {
    void send(ServerCommand clientCommand) throws IOException;
}
