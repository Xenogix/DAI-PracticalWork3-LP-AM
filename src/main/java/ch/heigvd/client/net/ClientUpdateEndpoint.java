package ch.heigvd.client.net;

import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.converter.CommandSerializer;

import java.io.IOException;
import java.net.*;

import static ch.heigvd.data.shared.Constants.PACKET_SIZE;

public class ClientUpdateEndpoint implements Runnable {
    private String multicastAddress;
    private int multicastPort;
    private final CommandHandler clientCommandHandler;
    private boolean isRunning = true;
    
    public ClientUpdateEndpoint(String multicastAddress, int multicastPort, CommandHandler clientCommandHandler){
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.clientCommandHandler = clientCommandHandler;
    }

    public void run(){
        isRunning = true;
        try(MulticastSocket socket = new MulticastSocket(multicastPort)){
            InetSocketAddress group = new InetSocketAddress(multicastAddress, multicastPort);
            socket.joinGroup(group, null);
            byte[] receiveData = new byte[PACKET_SIZE];
            while(isRunning){
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(packet);
                clientCommandHandler.handle(CommandSerializer.deserialize(receiveData));
            }
        } catch(IOException exception){
            throw new RuntimeException(exception); //Todo
        }
    }

    public void stop() {
        isRunning = false;
    }

    public CommandHandler getClientCommandHandler() {
        return clientCommandHandler;
    }

    public void setMulticastAddress(String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    public int getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }
}
