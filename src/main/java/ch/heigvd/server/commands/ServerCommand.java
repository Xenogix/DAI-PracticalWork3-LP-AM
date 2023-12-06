package ch.heigvd.server.commands;

import ch.heigvd.shared.abstractions.Command;

public class ServerCommand implements Command {

    private ServerCommandsType serverCommandType;
    private Object value;

    public ServerCommand(ServerCommandsType serverCommandsType, Object value){
        this.serverCommandType = serverCommandsType;
        this.value = value;
    }

    @Override
    public ServerCommandsType getCommandType() {
        return this.serverCommandType;
    }

    @Override
    public Object getValue() {
        return this.value;
    }


}
