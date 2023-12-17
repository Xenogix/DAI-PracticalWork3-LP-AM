package ch.heigvd.client;

import ch.heigvd.data.models.Game;

public class ClientStorage {
    private static ClientStorage instance;
    private String userId;
    private Game game;

    private ClientStorage() {

    }

    public static synchronized ClientStorage getInstance() {
        if(instance == null)
            instance = new ClientStorage();
        return  instance;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
}
