package ch.heigvd.shared.rules;

import ch.heigvd.shared.models.Input;

public class InputManager {
    private final GameEngine gameEngine;

    public InputManager(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void handleInput(String id, Input direction) {
    }
}
