package ch.heigvd.client;

import ch.heigvd.shared.models.Game;

public class ClientStorage {
    private static ClientStorage instance;
    private final Game game = new Game();

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
}
