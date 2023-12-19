package ch.heigvd;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.abstractions.VirtualUpdateServer;
import ch.heigvd.data.logs.Logger;
import ch.heigvd.data.shared.Constants;
import ch.heigvd.server.ServerStorage;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.server.commands.ServerUpdateListener;
import ch.heigvd.server.net.ServerCommandEndpoint;
import ch.heigvd.server.net.ServerUpdateSender;
import picocli.CommandLine;

@CommandLine.Command(name = "Snake server", description = "Start a server that can host an online game of snake")
public class MainServer implements Runnable {

    public static void main(String[] args) {
        new CommandLine(new MainServer()).execute(args);
    }

    @CommandLine.Option(names = { "-p", "--port" }, description = "Port of the server", required = false)
    private int serverPort = Constants.DEFAULT_SERVER_PORT;
    @CommandLine.Option(names = { "-ua", "--update_address" }, description = "Address for the multicast updates", required = false)
    private String multicastAddress = Constants.DEFAULT_UPDATE_ADDRESS;
    @CommandLine.Option(names = { "-up", "--update_port" }, description = "Port for the multicast updates", required = false)
    private int multicastPort = Constants.DEFAULT_UPDATE_PORT;

    public void run() {
        // Enable the logger
        Logger.setEnabled();

        // Initiate the unicast server
        ResponseCommandHandler commandHandler = new ServerCommandHandler();
        ServerCommandEndpoint testServer = new ServerCommandEndpoint(serverPort, commandHandler);

        // Initiate the multicast server and event handling
        ServerStorage storage = ServerStorage.getInstance();
        VirtualUpdateServer sender = new ServerUpdateSender(multicastAddress, multicastPort);
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
