package ch.heigvd.server.net;

import ch.heigvd.data.abstractions.ResponseCommandHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static ch.heigvd.data.shared.Constants.PACKET_SIZE;

public class ServerCommandEndpoint implements Runnable {
    private final int serverPort;
    private final ResponseCommandHandler commandHandler;
    private boolean isRunning = false;

    public ServerCommandEndpoint(int serverPort, ResponseCommandHandler commandHandler) {
        this.serverPort = serverPort;
        this.commandHandler = commandHandler;
    }

    @Override
    public void run(){
        isRunning = true;
        try(DatagramSocket socket = new DatagramSocket(serverPort)){
            byte[] data = new byte[PACKET_SIZE];
            while(isRunning){
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                new Thread(new CommandResponder(packet, socket, commandHandler)).start();
            }
        }catch (Exception e){

        }
    }

    public void stop() {
        isRunning = false;
    }
}
