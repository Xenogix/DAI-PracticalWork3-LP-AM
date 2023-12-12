package ch.heigvd.server.commands;

import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;

import java.util.UUID;

public class ServerCommandHandler{
    public Command handler(Command clientCommand){
        System.out.println(clientCommand.getValue());
        String userId = UUID.randomUUID().toString();
        return CommandFactory.getAcceptCommand(userId);
    }
}
