package ch.heigvd.shared.rules;

import ch.heigvd.shared.models.Game;
import ch.heigvd.shared.models.Direction;
import ch.heigvd.shared.models.Snake;

public class GameEngine {

    private static final int GAME_TPS = 10;
    private final Game game = new Game();

    public GameEngine() {

    }

    public void start() {

    }

    public void stop() {

    }

    public void setPlayerDirection(String id, Direction direction) {
        Snake snake = getSnakeByID(id);
        if(snake == null) return;
        snake.setDirection(direction);
    }

    private Snake getSnakeByID(String id) {
        for(Snake snake : game.getSnakes())
            if(snake.getId() == id) return snake;
        return null;
    }
}
