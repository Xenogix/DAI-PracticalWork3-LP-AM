package ch.heigvd.client.commands;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandType;

public class ClientUpdateHandler implements CommandHandler {
    @Override
    public void handle(Command command) {
        if(command.getCommandType() != CommandType.UPDATE) return;
        System.out.println(command.getValue());
    }
}
