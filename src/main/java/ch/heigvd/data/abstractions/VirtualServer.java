package ch.heigvd.data.abstractions;

import ch.heigvd.data.commands.Command;

import java.io.IOException;

public interface VirtualServer {
    void send(Command command) throws IOException;
}
