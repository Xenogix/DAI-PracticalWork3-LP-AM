package ch.heigvd.client.net;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.shared.abstractions.Command;
import ch.heigvd.shared.converter.CommandConverter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientCommandEndpoint {

    public final static short LISTENER_PORT = 32451;

    private final ClientStorage clientStorage = ClientStorage.getInstance();
    public void sendCommand(ClientCommand clientCommand){
        //Server socket creation
        try(DatagramSocket serverSocket = new DatagramSocket(LISTENER_PORT)){

            byte[] clientCommandData = CommandConverter.serialize(clientCommand);
            DatagramPacket sendPacket = new DatagramPacket(clientCommandData, clientCommandData.length, InetAddress.getByName(clientStorage.getServerAddress()), clientStorage.getServerPort());
            serverSocket.send(sendPacket);

            //Receive reply from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            Command receivedCommand = CommandConverter.deserialize(receiveData);


        }catch(IOException ex){
            throw new RuntimeException(ex); //Todo
        }
    }
}