package ch.heigvd.local;

import ch.heigvd.shared.models.Game;
import ch.heigvd.shared.rules.GameEngine;

class LocalStorage {
    private static LocalStorage instance;
    private final GameEngine gameEngine = new GameEngine();

    private LocalStorage() {

    }

    public static synchronized LocalStorage getInstance() {
        if(instance == null)
            instance = new LocalStorage();

        return instance;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }
}
