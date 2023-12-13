package ch.heigvd.data.rules;

import ch.heigvd.data.models.Input;

public class InputManager {
    private final GameEngine gameEngine;

    public InputManager(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void handleInput(String id, Input direction) {
    }
}
