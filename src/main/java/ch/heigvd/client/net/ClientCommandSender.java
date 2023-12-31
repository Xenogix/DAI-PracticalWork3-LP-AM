package ch.heigvd.client.net;

import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.converter.CommandSerializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientCommandSender implements VirtualClient {
    private final String serverAddress;
    private final int serverPort;

    public ClientCommandSender(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public Command send(Command command) throws IOException {

        if(command == null) return null;

        //Server socket creation
        try(DatagramSocket serverSocket = new DatagramSocket()){

            byte[] clientCommandData = CommandSerializer.serialize(command);
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);
            DatagramPacket sendPacket = new DatagramPacket(clientCommandData, clientCommandData.length,serverInetAddress , serverPort);
            serverSocket.send(sendPacket);

            //Receive reply from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            return CommandSerializer.deserialize(receiveData);

        } catch(SocketException ex){
            throw new RuntimeException(ex); //Todo
        }
    }
}