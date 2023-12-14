package ch.heigvd.local;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.models.Game;

import java.io.IOException;

public class LocalClient implements VirtualClient {
    private LocalLinker linker;
    private final CommandHandler updateHandler;

    public LocalClient(CommandHandler updateHandler) {
        this.linker = linker;
        this.updateHandler = updateHandler;
    }

    @Override
    public Command send(Command command) throws IOException {
        LocalServer server = linker.getServer();
        return server.forward(command);
    }

    public void forwardUpdate(Game game) {
        updateHandler.handle(CommandFactory.getUpdateCommand(game));
    }

    public void bind(LocalLinker linker) {
        this.linker = linker;
    }
}
