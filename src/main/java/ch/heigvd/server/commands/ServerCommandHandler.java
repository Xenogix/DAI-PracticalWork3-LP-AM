package ch.heigvd.server.commands;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.shared.abstractions.VirtualClient;

public class ServerCommandHandler{
    private final VirtualClient virtualClient;

    public ServerCommandHandler(VirtualClient virtualClient){
        this.virtualClient = virtualClient;
    }

    public void handler(ClientCommand clientCommand){

        //join

        //input
    }
}
