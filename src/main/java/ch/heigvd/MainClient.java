package ch.heigvd;

import ch.heigvd.client.net.ClientCommandEndpoint;
import ch.heigvd.data.commands.AcceptCommandData;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Input;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {

        String serverAddress = "localhost";
        int serverPort = 12643;
        ClientCommandEndpoint endpoint = new ClientCommandEndpoint(serverAddress, serverPort);

        Scanner s = new Scanner(System.in);

        try {
            Command serverCommand = endpoint.send(CommandFactory.getJoinCommand("My username", Color.Blue));
            String userId = ((AcceptCommandData)(serverCommand.getValue())).userId();
            System.out.printf("My user ID is : %s", userId);
            char input;
            while ((input = (char)System.in.read()) != 'q') {
                System.out.println(endpoint.send(CommandFactory.getInputCommand(userId, Input.UP_ARROW)));
            }
        }
        catch (IOException ex) {

        }
    }
}
