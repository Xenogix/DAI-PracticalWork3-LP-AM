package ch.heigvd.data.commands;

import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Input;

public class CommandFactory {
    public static Command getAcceptCommand(String userId) {
        return new Command(CommandType.ACCEPT, new AcceptCommandData(userId));
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
    public static Command getInputCommand(String userId, Input input) {
        return new Command(CommandType.INPUT, new InputCommandData(userId, input));
    }
}
