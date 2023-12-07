package ch.heigvd.shared.abstractions;

import ch.heigvd.client.commands.ClientCommand;

import java.io.IOException;

public interface ClientVirtualEndpoint {
    void send(ClientCommand clientCommand) throws IOException;
}
