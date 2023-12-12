package ch.heigvd.client.net;

import ch.heigvd.client.ClientStorage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UpdateClientHandler {

    private final ClientStorage clientStorage = ClientStorage.getInstance();

    public void update(){

        try(MulticastSocket socket = new MulticastSocket()){
            InetAddress multicastAddress = InetAddress.getByName();
            InetSocketAddress group = new InetSocketAddress();
            NetworkInterface networkInterface = NetworkInterface.getByName();
            socket.joinGroup(group, networkInterface);

            socket.joinGroup();

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
