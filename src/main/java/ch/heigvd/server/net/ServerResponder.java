package ch.heigvd.server.net;

import ch.heigvd.data.commands.Command;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.data.abstractions.ServerVirtualEndpoint;
import ch.heigvd.data.converter.CommandSerializer;

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
            Command clientCommand = CommandSerializer.deserialize(packet.getData());
            Command commandToSend = serverCommandHandler.handler(clientCommand);
            send(commandToSend);
        }
        catch (IOException ex) {
            //To do
        }
    }

    @Override
    public void send(Command serverCommand) throws IOException {
        if(serverCommand == null) return;
        byte[] data = CommandSerializer.serialize(serverCommand);
        DatagramPacket packetToSend = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
        socket.send(packetToSend);
    }
}
