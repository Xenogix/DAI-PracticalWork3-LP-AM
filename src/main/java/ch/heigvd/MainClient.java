package ch.heigvd;

import ch.heigvd.client.net.ClientCommandSender;
import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.CommandType;
import ch.heigvd.data.commands.data.AcceptCommandData;
import ch.heigvd.data.commands.data.UpdateCommandData;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Snake;

import java.util.Random;

public class MainClient {

    private static final String UPDATE_ADDRESS = "224.12.17.11";
    private static final String SERVER_ADDRESS = "localhost";
    private static final int UPDATE_PORT = 3433;
    private static final int SERVER_PORT = 3432;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        // Create the endpoint and command sender
        CommandHandler updateHandler = new DummyUpdateHandler();
        ClientUpdateEndpoint endpoint = new ClientUpdateEndpoint(UPDATE_ADDRESS, UPDATE_PORT, updateHandler);
        VirtualClient commandSender = new ClientCommandSender(SERVER_ADDRESS, SERVER_PORT);

        // Start listening updates
        Thread endpointThread = new Thread(endpoint);
        endpointThread.start();

        // Start dummy client inputs
        StartDummyInput(commandSender);
    }

    static void StartDummyInput(VirtualClient virtualClient) {
         try {
             // Send join command to the server
             Color[] colors = Color.values();
             Direction[] directions = Direction.values();
             int callCount = 0;
             while (true) {
                 String username = String.format("SnakePlayer%d", callCount);
                 Color color = colors[RANDOM.nextInt(colors.length)];
                 Direction input = directions[RANDOM.nextInt(directions.length)];
                 Command recievedCommand = virtualClient.send(CommandFactory.getJoinCommand(username, color));
                 if (recievedCommand.getCommandType() == CommandType.ACCEPT) {
                     AcceptCommandData data = (AcceptCommandData) recievedCommand.getValue();
                     virtualClient.send(CommandFactory.getInputCommand(data.userId(), input));
                 }

                 callCount++;
                 Thread.sleep(1000);
             }
         }
         catch (Exception ex) {

         }
    }

    static class DummyUpdateHandler implements CommandHandler {

        @Override
        public void handle(Command command) {
            if(command.getCommandType() != CommandType.UPDATE) return;
            Game game = ((UpdateCommandData)command.getValue()).game();

            try {
                System.out.print("\033[H\033[2J");
            }
            catch (Exception ex) {

            }

            System.out.printf("Snake Count : %d \n", game.getSnakes().size());
            System.out.printf("Apple Count : %d \n", game.getSnakes().size());
            System.out.println("\nSnake informations :");
            if(game.getSnakes().isEmpty()) System.out.println("no information");
            for(Snake snake : game.getSnakes()) {
                System.out.printf("ID : %s | Head pos : %d,%d | Color : %s\n",
                        snake.getUserId(),
                        snake.getHeadPosition().getX(),
                        snake.getHeadPosition().getY(),
                        snake.getColor());
            }
        }
    }
}
