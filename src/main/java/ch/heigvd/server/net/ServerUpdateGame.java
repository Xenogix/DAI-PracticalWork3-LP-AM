package ch.heigvd.server.net;

import java.io.IOException;
import java.net.*;

public class ServerUpdateGame {
    private final static short PORT = 1234;

    private final static String interfaceName = "e/0";

    public void sendUpdate(){

        try(MulticastSocket socket = new MulticastSocket()){

            InetAddress multicastAddress = InetAddress.getByName("230.0.0.0");
            InetSocketAddress group = new InetSocketAddress(multicastAddress, PORT);
            NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);

            try{
                byte[] data = new byte[];

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
