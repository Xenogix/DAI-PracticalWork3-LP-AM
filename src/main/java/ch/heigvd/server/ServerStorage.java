package ch.heigvd.server;

import ch.heigvd.shared.models.Game;
import ch.heigvd.shared.rules.GameEngine;

class ServerStorage {
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
