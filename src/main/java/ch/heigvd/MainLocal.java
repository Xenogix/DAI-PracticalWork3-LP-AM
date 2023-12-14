package ch.heigvd;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.local.LocalClient;
import ch.heigvd.local.LocalLinker;
import ch.heigvd.local.LocalServer;
import ch.heigvd.server.ServerStorage;
import ch.heigvd.server.commands.ServerCommandHandler;

public class MainLocal {
    public static void main(String[] args) {

        // Create the local server
        ResponseCommandHandler serverCommandHandler = new ServerCommandHandler();
        LocalServer server = new LocalServer(serverCommandHandler);

        // Create the local client
        CommandHandler updateHandler = new MainClient.DummyUpdateHandler();
        LocalClient client = new LocalClient(updateHandler);

        // Bind the endpoints to the linker
        LocalLinker linker = new LocalLinker(server, client);
        linker.bind();

        // Register the update listener
        ServerStorage storage = ServerStorage.getInstance();
        GameUpdateListener gameUpdateListener = new MainServer.DummyGameUpdateListener(linker.getServer());
        storage.getGameEngine().addListner(gameUpdateListener);

        // Start game engine
        Thread gameThread = new Thread(storage.getGameEngine());
        gameThread.start();

        // Start the simulation
        MainClient.StartDummyInput(linker.getClient());
    }
}
