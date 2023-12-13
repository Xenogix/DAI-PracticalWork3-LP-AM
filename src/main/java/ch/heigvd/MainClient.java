package ch.heigvd;

import ch.heigvd.client.commands.ClientUpdateHandler;
import ch.heigvd.client.net.ClientCommandSender;
import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.commands.data.AcceptCommandData;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Input;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        startTestMulticast();
    }

    private static void startTestMulticast() {
        CommandHandler commandHandler = new CommandHandler() {
            @Override
            public void handle(Command command) {
                System.out.println(command.getValue());
            }
        };

        ClientUpdateEndpoint endpoint = new ClientUpdateEndpoint("224.12.17.11", 3432, commandHandler);
        endpoint.start();
    }

    private static void startTestUnicast() {

        String serverAddress = "localhost";
        int serverPort = 12643;
        ClientCommandSender sender = new ClientCommandSender(serverAddress, serverPort);

        Scanner s = new Scanner(System.in);

        try {
            Command serverCommand = sender.send(CommandFactory.getJoinCommand("My username", Color.Blue));
            String userId = ((AcceptCommandData)(serverCommand.getValue())).userId();
            System.out.printf("My user ID is : %s", userId);

            char inputChar;
            while ((inputChar = (char)System.in.read()) != 'q') {

                Input input = switch (inputChar) {
                    case 'w' -> Input.UP_ARROW;
                    case 'a' -> Input.LEFT_ARROW;
                    case 's' -> Input.DOWN_ARROW;
                    case 'd' -> Input.RIGHT_ARROW;
                    default -> Input.DOWN_ARROW;
                };

                Command receivedCommand = sender.send(CommandFactory.getInputCommand(userId, input));
                System.out.println(receivedCommand.getValue());
            }
        }
        catch (IOException ex) {

        }
    }
}
