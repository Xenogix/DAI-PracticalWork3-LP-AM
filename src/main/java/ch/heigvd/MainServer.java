package ch.heigvd;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.abstractions.VirtualUpdateServer;
import ch.heigvd.data.logs.Logger;
import ch.heigvd.server.ServerStorage;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.server.commands.ServerUpdateListener;
import ch.heigvd.server.net.ServerCommandEndpoint;
import ch.heigvd.server.net.ServerUpdateSender;

public class MainServer {
    private static final String UPDATE_ADDRESS = "224.12.17.11";
    private static final int UPDATE_PORT = 3433;
    private static final int SERVER_PORT = 3432;
    public static void main(String[] args) {

        // Enable the logger
        Logger.setEnabled();

        // Initiate the unicast server
        ResponseCommandHandler commandHandler = new ServerCommandHandler();
        ServerCommandEndpoint testServer = new ServerCommandEndpoint(SERVER_PORT, commandHandler);

        // Initiate the multicast server and event handling
        ServerStorage storage = ServerStorage.getInstance();
        VirtualUpdateServer sender = new ServerUpdateSender(UPDATE_ADDRESS, UPDATE_PORT);
        GameUpdateListener listener = new ServerUpdateListener(sender);
        storage.getGameEngine().addListner(listener);

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
}
