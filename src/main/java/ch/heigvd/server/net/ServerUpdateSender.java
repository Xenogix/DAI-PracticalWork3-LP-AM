package ch.heigvd.server.net;

import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.converter.CommandSerializer;
import ch.heigvd.data.models.Game;

import java.io.IOException;
import java.net.*;

public class ServerUpdateSender {
    private final String multicastAddress;
    private final int multicastPort;

    public ServerUpdateSender(String multicastAddress, int multicastPort) {
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    public void send(Game game){

        try(DatagramSocket socket = new DatagramSocket()){

            InetAddress multicastAddress = InetAddress.getByName(this.multicastAddress);
            InetSocketAddress group = new InetSocketAddress(multicastAddress, multicastPort);

            try{
                Command commandToSend = CommandFactory.getUpdateCommand(game);
                byte[] data = CommandSerializer.serialize(commandToSend);
                DatagramPacket datagram = new DatagramPacket(data, data.length, group);
                socket.send(datagram);

            }catch (IOException exception){
                throw new RuntimeException(exception);
            }
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
