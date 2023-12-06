package ch.heigvd.server.net;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.server.commands.ServerCommandHandler;
import ch.heigvd.shared.abstractions.Command;
import ch.heigvd.shared.abstractions.VirtualClient;
import ch.heigvd.shared.converter.CommandConverter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerResponder implements Runnable, VirtualClient {
    private final DatagramPacket packet;
    private final DatagramSocket socket;

    private final ServerCommandHandler serverCommandHandler = new ServerCommandHandler(this);

    public ServerResponder(DatagramPacket packet, DatagramSocket socket){
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    public void run() {
        if(CommandConverter.deserialize(packet.getData())instanceof ClientCommand clientCommand)
            serverCommandHandler.handler(clientCommand);

    }

    @Override
    public void send(Command command) {
        try{
            byte[] data = CommandConverter.serialize(command);
            DatagramPacket packetToSend = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
            socket.send(packetToSend);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
