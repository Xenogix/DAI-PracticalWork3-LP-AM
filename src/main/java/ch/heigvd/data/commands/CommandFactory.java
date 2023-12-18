package ch.heigvd.data.commands;

import ch.heigvd.data.commands.data.*;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.models.Game;

public class CommandFactory {
    public static Command getAcceptCommand(String userId) {
        return new Command(CommandType.ACCEPT, new AcceptCommandData(userId));
    }
    public static Command getAcknowledgeCommand() {
        return new Command(CommandType.ACKNOWLEDGE, new AcknowledgeCommandData());
    }
    public static Command getRefuseCommand(String message) {
        return new Command(CommandType.REFUSE, new RefuseCommandData(message));
    }
    public static Command getUpdateCommand(Game game) {
        return new Command(CommandType.UPDATE, new UpdateCommandData(game));
    }
    public static Command getJoinCommand(String username, Color snakeColor) {
        return new Command(CommandType.JOIN, new JoinCommandData(username, snakeColor));
    }
    public static Command getInputCommand(String userId, Direction direction) {
        return new Command(CommandType.INPUT, new InputCommandData(userId, direction));
    }
}
