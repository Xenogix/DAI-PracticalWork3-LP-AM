package ch.heigvd.client;

import ch.heigvd.shared.models.Game;

class ClientStorage {
    private static ClientStorage instance;
    private Game game;

    private ClientStorage() {

    }

    public static synchronized ClientStorage getInstance() {
        if(instance == null)
            instance = new ClientStorage();
        return  instance;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
