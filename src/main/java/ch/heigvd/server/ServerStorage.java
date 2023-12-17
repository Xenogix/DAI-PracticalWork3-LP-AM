package ch.heigvd.server;

import ch.heigvd.data.game.GameEngine;

public class ServerStorage {
    private static ServerStorage instance;
    private final GameEngine game = new GameEngine();

    private ServerStorage() {
    }

    public static synchronized ServerStorage getInstance() {
        if(instance == null)
            instance = new ServerStorage();
        return  instance;
    }

    public GameEngine getGameEngine() {
        return game;
    }
}
