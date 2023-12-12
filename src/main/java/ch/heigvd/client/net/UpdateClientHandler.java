package ch.heigvd.client.net;

import ch.heigvd.client.ClientStorage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UpdateClientHandler {

    private final static short PORT = 1234;
    private final static String interfaceName = "e/0";

    private final static String HOST = "host";

    private final ClientStorage clientStorage = ClientStorage.getInstance();

    public void update(){

        try(MulticastSocket socket = new MulticastSocket()){
            InetAddress multicastAddress = InetAddress.getByName(HOST);
            InetSocketAddress group = new InetSocketAddress("230.0.0.0", PORT);
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
