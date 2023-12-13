package ch.heigvd.client.net;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.converter.CommandSerializer;

import java.io.IOException;
import java.net.*;

public class ClientUpdateEndpoint {
    private final String multicastAddress;
    private final int multicastPort;
    private final CommandHandler clientCommandHandler;

    public ClientUpdateEndpoint(String multicastAddress, int multicastPort, CommandHandler clientCommandHandler){
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.clientCommandHandler = clientCommandHandler;
    }

    public void start(){

        try(MulticastSocket socket = new MulticastSocket(multicastPort)){
            InetSocketAddress group = new InetSocketAddress(multicastAddress, multicastPort);
            socket.joinGroup(group, null);

            byte[] receiveData = new byte[1024];

            while(true){
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(packet);
                clientCommandHandler.handle(CommandSerializer.deserialize(receiveData));
            }
        } catch(IOException exception){
            throw new RuntimeException(exception); //Todo
        }
    }
}
