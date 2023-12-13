package ch.heigvd.server.net;

import ch.heigvd.data.abstractions.ResponseCommandHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerCommandEndpoint {
    private final int serverPort;
    private final ResponseCommandHandler commandHandler;

    public ServerCommandEndpoint(int serverPort, ResponseCommandHandler commandHandler) {
        this.serverPort = serverPort;
        this.commandHandler = commandHandler;
    }

    public void start(){
        try(DatagramSocket socket = new DatagramSocket(serverPort)){

            while(true){
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);

                new Thread(new CommandResponder(packet, socket, commandHandler)).start();
            }
        }catch (Exception e){

        }
    }
}
