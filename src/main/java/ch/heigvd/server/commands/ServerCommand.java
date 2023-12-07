package ch.heigvd.server.commands;

import ch.heigvd.shared.abstractions.Command;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerCommand implements Command {

    private ServerCommandsType serverCommandType;
    private Object value;

    public ServerCommand(@JsonProperty("command_type")  ServerCommandsType serverCommandsType, @JsonProperty("value") Object value){
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
