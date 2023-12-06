package ch.heigvd.client.commands;

import ch.heigvd.shared.abstractions.Command;

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
    public ClientCommand(ClientCommandsType clientCommandsType, Object value){
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
