package ch.heigvd.server.net;

import java.io.IOException;
import java.net.*;

public class ServerUpdateGame {
    private final static short PORT = 1234;

    public void sendUpdate(){

        try(DatagramSocket socket = new DatagramSocket()){

            InetAddress multicastAddress = InetAddress.getByName("230.0.0.0");
            InetSocketAddress group = new InetSocketAddress(multicastAddress, PORT);

            try{
                byte[] data = new byte[1024]; //todo what do we want to send

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
