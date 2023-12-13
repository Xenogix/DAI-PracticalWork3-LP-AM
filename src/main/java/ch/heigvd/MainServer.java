package ch.heigvd;

import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.data.AcceptCommandData;
import ch.heigvd.data.models.Game;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.server.net.ServerCommandEndpoint;
import ch.heigvd.server.net.ServerUpdateSender;

import java.util.UUID;

public class MainServer {
    public static void main(String[] args){
        startTestMulticast();
    }

    private static void startTestMulticast() {
        ServerUpdateSender sender = new ServerUpdateSender("224.12.17.11", 3432);

        while(true) {
            sender.send(new Game());

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {

            }
        }
    }

    public static void startTestUnitcast() {
        ResponseCommandHandler handler = new ResponseCommandHandler() {
            @Override
            public Command handle(Command command) {
                String userId = UUID.randomUUID().toString();
                return CommandFactory.getAcceptCommand(userId);
            }
        };

        ServerCommandEndpoint testServer = new ServerCommandEndpoint(12643, handler);
        testServer.start();
    }
}
