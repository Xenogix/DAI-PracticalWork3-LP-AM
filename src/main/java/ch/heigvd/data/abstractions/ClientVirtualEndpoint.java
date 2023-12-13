package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

import java.io.IOException;

public interface ClientVirtualEndpoint {
    Command send(Command clientCommand) throws IOException;
}
