package ch.heigvd.data.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Command {
    @JsonProperty("commandType")
    private final CommandType commandType;
    @JsonProperty("value")
    private Object value;

    @JsonCreator
    public Command(@JsonProperty("commandType") CommandType commandType, @JsonProperty("value") Object value) {
        this.commandType = commandType;
        this.value = value;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
