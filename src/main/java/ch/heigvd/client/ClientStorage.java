package ch.heigvd.client;

import ch.heigvd.shared.models.Game;

public class ClientStorage {
    private static ClientStorage instance;
    private final Game game = new Game();
    private String clientId;
    private String serverAddress ;

    private short serverPort;

    private ClientStorage() {

    }

    public static synchronized ClientStorage getInstance() {
        if(instance == null)
            instance = new ClientStorage();

        return  instance;
    }

    public Game getGame() {
        return game;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public String getServerAddress(){
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress){
        this.serverAddress = serverAddress;
    }

    public short getServerPort() {
        return serverPort;
    }

    public void setServerPort(short serverPort) {
        this.serverPort = serverPort;
    }


}
