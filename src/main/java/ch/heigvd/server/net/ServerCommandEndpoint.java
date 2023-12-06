package ch.heigvd.server.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerCommandEndpoint {
    private static final short PORT = 23102;



    public void start(){
        try(DatagramSocket socket = new DatagramSocket(PORT)){

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
