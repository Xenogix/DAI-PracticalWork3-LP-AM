package ch.heigvd.server.commands;

import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.data.InputCommandData;
import ch.heigvd.data.commands.data.JoinCommandData;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.game.GameEngine;
import ch.heigvd.server.ServerStorage;

public class ServerCommandHandler implements ResponseCommandHandler {
    private final ServerStorage storage = ServerStorage.getInstance();

    public Command handle(Command command){
        return switch (command.getCommandType()) {
            case JOIN -> HandleJoinCommand((JoinCommandData)command.getValue());
            case INPUT -> HandleInputCommand((InputCommandData)command.getValue());
            default -> HandleUnsupportedCommand();
        };
    }

    private Command HandleJoinCommand(JoinCommandData data) {
        GameEngine engine = storage.getGameEngine();

        // Get a new ID and add the player to the game
        String userId = engine.getNewSnakeID();
        boolean wasSuccessful = engine.spawnSnake(userId, data.username(), data.snakeColor());

        // If the operation was successful return an accept command
        // else return a refuse command
        if(wasSuccessful)
            return CommandFactory.getAcceptCommand(userId);
        else
            return CommandFactory.getRefuseCommand("Unable to join the game");
    }

    private Command HandleInputCommand(InputCommandData data) {
        GameEngine engine = storage.getGameEngine();

        // Check if the input is a direction
        boolean wasOperationSuccessful = false;
        Direction direction = data.direction();
        if(direction != null) wasOperationSuccessful = engine.setSnakeDirection(data.userId(), direction);

        // If the operation was successful return an accept command
        // else return a refuse command
        if(wasOperationSuccessful)
            return CommandFactory.getAcceptCommand(data.userId());
        else
            return CommandFactory.getRefuseCommand("Failed to process input");
    }

    private Command HandleUnsupportedCommand() {
        return CommandFactory.getRefuseCommand("The provided command is not supported");
    }
}
