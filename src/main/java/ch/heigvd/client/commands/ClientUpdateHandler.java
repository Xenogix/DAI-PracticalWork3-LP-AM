package ch.heigvd.client.commands;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandType;
import ch.heigvd.data.commands.data.UpdateCommandData;
import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Snake;

public class ClientUpdateHandler implements CommandHandler {
    @Override
    public void handle(Command command) {
        if(command.getCommandType() != CommandType.UPDATE) return;
        Game game = ((UpdateCommandData)command.getValue()).game();
    }
}
