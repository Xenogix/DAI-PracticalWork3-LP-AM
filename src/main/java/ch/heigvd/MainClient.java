package ch.heigvd;

import ch.heigvd.server.net.ServerCommandEndpoint;

public class MainClient {
    public static void main(String[] args){
        ServerCommandEndpoint testServer = new ServerCommandEndpoint();
        testServer.start();
    }
}
