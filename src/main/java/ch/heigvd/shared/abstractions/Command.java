package ch.heigvd.shared.abstractions;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Command {
    @JsonProperty("command_type")
    CommandType getCommandType();
    @JsonProperty("value")
    Object getValue();
}
