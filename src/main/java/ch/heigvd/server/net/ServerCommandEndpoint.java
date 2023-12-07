package ch.heigvd.server.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerCommandEndpoint {

    private final int serverPort;

    public ServerCommandEndpoint(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        try(DatagramSocket socket = new DatagramSocket(serverPort)){

            while(true){
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);

                new Thread(new ServerResponder(packet, socket)).start();
            }
        }catch (Exception e){

        }
    }
}
