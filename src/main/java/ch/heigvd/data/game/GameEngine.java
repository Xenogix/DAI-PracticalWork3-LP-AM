package ch.heigvd.data.game;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.models.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class GameEngine implements Runnable {
    private static final int GAME_TPS = 10;
    private static final int MAX_PLAYER_COUNT = 10;
    private static final int GAME_BOARD_SIZE = 100;
    private final Game game = new Game(GAME_BOARD_SIZE, GAME_BOARD_SIZE);
    private final ArrayList<GameUpdateListener> gameUpdateListeners = new ArrayList<>();
    private final Random random = new Random();
    private boolean isRunning = false;

    public void run() {
        long now;
        long updateTime;
        long wait;
        long optimal_time = 1000000000 / GAME_TPS;

        isRunning = true;
        while (isRunning) {
            now = System.nanoTime();

            doTick();
            notifyListners();

            updateTime = System.nanoTime() - now;
            wait = (optimal_time - updateTime) / 1000000;

            try {
                if(wait > 0) Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void addListner(GameUpdateListener gameUpdateListener) {
        gameUpdateListeners.add(gameUpdateListener);
    }

    public void removeListner(GameUpdateListener gameUpdateListener) {
        gameUpdateListeners.remove(gameUpdateListener);
    }

    public void notifyListners() {
        for (GameUpdateListener listner : gameUpdateListeners) {
            listner.gameUpdated(game);
        }
    }

    public boolean spawnSnake(String snakeId, String username, Color color) {
        if(game.getSnakes().size() >= MAX_PLAYER_COUNT) return false;
        Point spawnPoint = getSpawnPoint();
        Snake snakeToAdd = new Snake(snakeId, username, spawnPoint, color);
        game.getSnakes().add(snakeToAdd);
        return true;
    }

    public boolean setSnakeDirection(String snakeId, Direction direction) {
        Snake snake = getSnakeByID(snakeId);
        if(snake == null) return false;
        snake.setDirection(direction);
        return true;
    }

    public String getNewSnakeID() {
        return UUID.randomUUID().toString();
    }

    private void resetGame() {
        game.getSnakes().clear();
    }

    private void doTick() {
        applySnakeMovement();
        applySnakeCollisions();
    }

    private void applySnakeMovement() {
        for (Snake snake : game.getSnakes()) {
            if(snake.getDirection() == null) return;
            Point pos = snake.getHeadPosition();
            switch (snake.getDirection())
            {
                case UP -> snake.setHeadPosition(new Point(pos.getX(), pos.getY() + 1));
                case DOWN -> snake.setHeadPosition(new Point(pos.getX(), pos.getY() - 1));
                case LEFT -> snake.setHeadPosition(new Point(pos.getX() - 1, pos.getY()));
                case RIGHT -> snake.setHeadPosition(new Point(pos.getX() + 1, pos.getY()));
            }
        }
    }

    private void applySnakeCollisions() {
        // TODO : Create the snake body and collision
    }

    private Snake getSnakeByID(String snakeId) {
        for(Snake snake : game.getSnakes())
            if(snake.getUserId().equals(snakeId)) return snake;
        return null;
    }

    private Point getSpawnPoint() {
        // TODO : Improve the algorithm
        Board board = game.getBoard();
        int x = random.nextInt(0, board.getWidth());
        int y = random.nextInt(0, board.getHeight());
        return new Point(x,y);
    }
}
