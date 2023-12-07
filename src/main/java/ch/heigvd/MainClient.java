package ch.heigvd;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.client.commands.ClientCommandsType;
import ch.heigvd.client.net.ClientCommandEndpoint;

import java.io.IOException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {

        String serverAddress = "localhost";
        int serverPort = 12643;
        ClientCommandEndpoint endpoint = new ClientCommandEndpoint(serverAddress, serverPort);

        Scanner s = new Scanner(System.in);

        try {
            char input;
            while ((input = (char)System.in.read()) != 'q') {
                endpoint.send(new ClientCommand(ClientCommandsType.INPUT, input));
            }
        }
        catch (IOException ex) {

        }
    }
}
