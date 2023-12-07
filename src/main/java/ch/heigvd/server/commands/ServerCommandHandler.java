package ch.heigvd.server.commands;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.shared.abstractions.ServerVirtualEndpoint;

public class ServerCommandHandler{
    public ServerCommand handler(ClientCommand clientCommand){
        System.out.println(clientCommand.getValue());
        return new ServerCommand(ServerCommandsType.ACCEPT, "accept");
    }
}
