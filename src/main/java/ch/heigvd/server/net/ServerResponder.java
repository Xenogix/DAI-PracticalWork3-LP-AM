package ch.heigvd.server.net;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.server.commands.ServerCommand;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.shared.abstractions.Command;
import ch.heigvd.shared.abstractions.ServerVirtualEndpoint;
import ch.heigvd.shared.converter.CommandConverter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerResponder implements Runnable, ServerVirtualEndpoint {
    private final DatagramPacket packet;
    private final DatagramSocket socket;
    private final ServerCommandHandler serverCommandHandler = new ServerCommandHandler();

    public ServerResponder(DatagramPacket packet, DatagramSocket socket){
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ClientCommand clientCommand = CommandConverter.<ClientCommand>deserialize(packet.getData(), ClientCommand.class);
            ServerCommand commandToSend = serverCommandHandler.handler(clientCommand);
            send(commandToSend);
        }
        catch (IOException ex) {
            //To do
        }
    }

    @Override
    public void send(ServerCommand serverCommand) throws IOException {
        if(serverCommand == null) return;
        byte[] data = CommandConverter.serialize(serverCommand);
        DatagramPacket packetToSend = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
        socket.send(packetToSend);
    }
}
