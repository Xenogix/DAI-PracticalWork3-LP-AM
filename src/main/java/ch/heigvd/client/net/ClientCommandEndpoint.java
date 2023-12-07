package ch.heigvd.client.net;

import ch.heigvd.client.commands.ClientCommand;
import ch.heigvd.client.commands.ClientCommandHandler;
import ch.heigvd.server.commands.ServerCommand;
import ch.heigvd.shared.abstractions.ClientVirtualEndpoint;
import ch.heigvd.shared.abstractions.Command;
import ch.heigvd.shared.converter.CommandConverter;

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
    public void send(ClientCommand clientCommand) throws IOException {

        if(clientCommand == null) return;

        //Server socket creation
        try(DatagramSocket serverSocket = new DatagramSocket(LISTENER_PORT)){

            byte[] clientCommandData = CommandConverter.serialize(clientCommand);
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);
            DatagramPacket sendPacket = new DatagramPacket(clientCommandData, clientCommandData.length,serverInetAddress , serverPort);
            serverSocket.send(sendPacket);

            //Receive reply from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            ServerCommand receivedCommand = CommandConverter.<ServerCommand>deserialize(receiveData, ServerCommand.class);

        } catch(SocketException ex){
            throw new RuntimeException(ex); //Todo
        }
    }
}