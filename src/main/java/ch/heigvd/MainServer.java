package ch.heigvd;

import ch.heigvd.server.net.ServerCommandEndpoint;

public class MainServer {
    public static void main(String[] args){
        ServerCommandEndpoint testServer = new ServerCommandEndpoint(12643);
        testServer.start();
    }
}
