package ch.heigvd.server;

import ch.heigvd.shared.models.Game;

public class ServerStorage {
    private static ServerStorage instance;
    private final Game game = new Game();

    private ServerStorage() {
    }

    public static synchronized ServerStorage getInstance() {
        if(instance == null)
            instance = new ServerStorage();
        return  instance;
    }

    public Game getGame() {
        return game;
    }
}
