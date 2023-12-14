package ch.heigvd;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.VirtualServer;
import ch.heigvd.data.models.Game;
import ch.heigvd.server.ServerStorage;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.server.net.ServerCommandEndpoint;
import ch.heigvd.server.net.ServerUpdateSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainServer {
    private static final String UPDATE_ADDRESS = "224.12.17.11";
    private static final int UPDATE_PORT = 3433;
    private static final int SERVER_PORT = 3432;
    public static void main(String[] args) {

        // Initiate the unicast server
        ServerCommandHandler commandHandler = new ServerCommandHandler();
        ServerCommandEndpoint testServer = new ServerCommandEndpoint(SERVER_PORT, commandHandler);

        // Initiate the multicast server and event handling
        ServerStorage storage = ServerStorage.getInstance();
        ServerUpdateSender sender = new ServerUpdateSender(UPDATE_ADDRESS, UPDATE_PORT);
        GameUpdateListener gameUpdateListener = new DummyGameUpdateListener(sender);
        storage.getGameEngine().addListner(gameUpdateListener);

        // Start threads
        Thread endpointThread = new Thread(testServer);
        Thread gameThread = new Thread(storage.getGameEngine());
        endpointThread.start();
        gameThread.start();

        try {
            endpointThread.join();
            gameThread.join();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static class DummyGameUpdateListener implements GameUpdateListener {
        private final ServerUpdateSender updateSender;
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private Future<?> updateTask;

        public DummyGameUpdateListener(ServerUpdateSender updateSender) {
            this.updateSender = updateSender;
        }

        @Override
        public void gameUpdated(Game game) {
            if (updateTask == null || updateTask.isDone()) {
                updateTask = executorService.submit(() -> updateSender.send(game));
            }
        }
    }
}
