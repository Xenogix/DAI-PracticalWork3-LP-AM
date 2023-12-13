package ch.heigvd.server.commands;

import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.abstractions.ServerVirtualEndpoint;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.data.InputCommandData;
import ch.heigvd.data.commands.data.JoinCommandData;

import java.util.UUID;

public class ServerCommandHandler implements ResponseCommandHandler {

    public Command handle(Command command){

        return switch (command.getCommandType()) {
            case JOIN -> HandleJoinCommand((JoinCommandData)command.getValue());
            case INPUT -> HandleInputCommand((InputCommandData)command.getValue());
            default -> HandleUnsupportedCommand();
        };
    }

    private Command HandleJoinCommand(JoinCommandData data) {
        String userId = UUID.randomUUID().toString();
        // TODO : Add player to the game
        return CommandFactory.getAcceptCommand(userId);
    }

    private Command HandleInputCommand(InputCommandData data) {
        // TODO : Handle player input
        return null;
    }

    private Command HandleUnsupportedCommand() {
        return null;
    }
}
