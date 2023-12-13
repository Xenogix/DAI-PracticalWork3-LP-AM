package ch.heigvd.client.net;

import ch.heigvd.client.ClientStorage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UpdateClientHandler {

    private final static short PORT = 1234;
    private final static String interfaceName = "e/0"; //todo

    private final static String HOST = "localhost"; //todo

    private final ClientStorage clientStorage = ClientStorage.getInstance();

    private final String serverAddress;
    private final String serverPort;

    public UpdateClientHandler(String serverAddress, String serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void update(){

        try(MulticastSocket socket = new MulticastSocket()){
            InetAddress multicastAddress = InetAddress.getByName(serverAddress);
            InetSocketAddress group = new InetSocketAddress(multicastAddress, Integer.parseInt(serverPort));
            NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
            socket.joinGroup(group, networkInterface);

            byte[] receiveData = new byte[1024];

            while(true){
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(packet);

                String message = new String(
                        packet.getData(),
                        packet.getOffset(),
                        packet.getLength(),
                        StandardCharsets.UTF_8
                );
            }
        }catch(IOException exception){
            throw new RuntimeException(exception); //Todo
        }
    }
}
