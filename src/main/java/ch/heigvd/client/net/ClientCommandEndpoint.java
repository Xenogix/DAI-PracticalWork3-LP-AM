package ch.heigvd.client.net;

import ch.heigvd.data.abstractions.ClientVirtualEndpoint;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.converter.CommandSerializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientCommandEndpoint implements ClientVirtualEndpoint {
    public final static short LISTENER_PORT = 32451;
    private final String serverAddress;
    private final int serverPort;

    public ClientCommandEndpoint(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public Command send(Command clientCommand) throws IOException {

        if(clientCommand == null) return null;

        //Server socket creation
        try(DatagramSocket serverSocket = new DatagramSocket(LISTENER_PORT)){

            byte[] clientCommandData = CommandSerializer.serialize(clientCommand);
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