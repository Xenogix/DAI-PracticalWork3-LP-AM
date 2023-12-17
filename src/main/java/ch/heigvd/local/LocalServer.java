package ch.heigvd.local;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.abstractions.VirtualUpdateServer;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.models.Game;

import java.io.IOException;

public class LocalServer implements VirtualUpdateServer {
    private LocalLinker linker;
    private final ResponseCommandHandler commandHandler;

    public LocalServer(ResponseCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void send(Game game) {
        LocalClient client = linker.getClient();
        client.forwardUpdate(game);
    }

    public Command forward(Command command) {
        return commandHandler.handle(command);
    }

    public void bind(LocalLinker linker) {
        this.linker = linker;
    }

}
