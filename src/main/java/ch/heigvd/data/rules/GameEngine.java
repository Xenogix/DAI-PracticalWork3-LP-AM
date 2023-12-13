package ch.heigvd.data.rules;

import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.models.Snake;

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
            if(snake.getUserId() == id) return snake;
        return null;
    }
}
