package ch.heigvd.client.commands;

import ch.heigvd.shared.abstractions.Command;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCommand implements Command {

    /**
     * Type of the command.
     */
    private ClientCommandsType clientCommandsType;

    /**
     * Data given by the command.
     */
    private Object value;

    /**
     * Constructor.
     *
     * @param clientCommandsType
     * @param value
     */

    public ClientCommand( @JsonProperty("command_type") ClientCommandsType clientCommandsType, @JsonProperty("value") Object value){
        this.clientCommandsType = clientCommandsType;
        this.value = value;
    }

    @Override
    public ClientCommandsType getCommandType() {
        return this.clientCommandsType;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
