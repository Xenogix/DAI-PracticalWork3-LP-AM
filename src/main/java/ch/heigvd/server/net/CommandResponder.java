package ch.heigvd.server.net;

import ch.heigvd.data.abstractions.ResponseCommandHandler;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.abstractions.ServerVirtualEndpoint;
import ch.heigvd.data.converter.CommandSerializer;
import ch.heigvd.server.commands.ServerCommandHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CommandResponder implements Runnable, ServerVirtualEndpoint {
    private final DatagramPacket packet;
    private final DatagramSocket socket;
    private final ResponseCommandHandler commandHandler;

    public CommandResponder(DatagramPacket packet, DatagramSocket socket, ResponseCommandHandler commandHandler){
        this.packet = packet;
        this.socket = socket;
        this.commandHandler = commandHandler;
    }

    @Override
    public void run() {
        Command clientCommand = CommandSerializer.deserialize(packet.getData());
        Command commandToSend = commandHandler.handle(clientCommand);

        try {
            send(commandToSend);
        }
        catch (IOException ex) {

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
