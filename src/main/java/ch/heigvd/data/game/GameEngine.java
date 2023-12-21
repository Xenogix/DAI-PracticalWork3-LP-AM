package ch.heigvd.data.game;

import ch.heigvd.data.abstractions.GameUpdateListener;
import ch.heigvd.data.models.*;

import java.util.*;

public class GameEngine implements Runnable {
    private static final int GAME_TPS = 10;
    private static final int MAX_PLAYER_COUNT = 10;
    private static final int GAME_BOARD_SIZE = 100; //todo checks if its the same as the ui
    private static final int SNAKE_SPAWN_SIZE = 4;
    private static final int APPLE_COUNT = 8;
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
        Snake snakeToAdd = new Snake(snakeId, username, spawnPoint, color, SNAKE_SPAWN_SIZE);
        game.getSnakes().add(snakeToAdd);
        return true;
    }

    public boolean killSnake(Snake snake) {
        return game.getSnakes().remove(snake);
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
        applySpawnApple();
    }

    private void applySpawnApple() {
        while(game.getApples().size() < APPLE_COUNT) {
            Random random = new Random();
            int power = random.nextInt(1, 5);

            Point position = getSpawnPoint();
            game.getApples().add(new Apple(position, power));
        }
    }

    private void applySnakeMovement() {
        List<Snake> tempSnakeCollection = new ArrayList<>(game.getSnakes());
        for(Snake snake : tempSnakeCollection){
            if(snake.getDirection() == null) return;

            //move the body
            for(int i = snake.getLength() - 1; i > 0; --i){
                int x = snake.getBody()[i-1].getX();
                int y = snake.getBody()[i-1].getY();
                snake.getBody()[i].setPoint(new Point(x,y));
            }

            //move the head
            Point posHead = snake.getHeadPosition();
            switch(snake.getDirection()){
                case UP -> snake.setHeadPosition(new Point(posHead.getX(), posHead.getY() - 1));
                case DOWN -> snake.setHeadPosition(new Point(posHead.getX(), posHead.getY() + 1));
                case LEFT -> snake.setHeadPosition(new Point(posHead.getX() - 1, posHead.getY()));
                case RIGHT -> snake.setHeadPosition(new Point(posHead.getX() + 1, posHead.getY()));
            }
            snake.getBody()[0].setX(snake.getHeadPosition().getX());
            snake.getBody()[0].setY(snake.getHeadPosition().getY());
        }
    }

    private void applySnakeCollisions() {
        checkBorderCollision();
        checkAppleCollision();
        checkBattleCollision();
    }

    private void checkBorderCollision(){
        List<Snake> tempSnakeCollection = new ArrayList<>(game.getSnakes());

        for(Snake snake : tempSnakeCollection){
            //Checks if head collide left border
            if(snake.getHeadPosition().getX() < 0)
                killSnake(snake);
            //Checks if head collide with right border
            if(snake.getHeadPosition().getX() > GAME_BOARD_SIZE)
                killSnake(snake);
            //Checks if head collide with top border
            if(snake.getHeadPosition().getY() < 0)
                killSnake(snake);
            //Checks if head collide with bottom border
            if(snake.getHeadPosition().getY() > GAME_BOARD_SIZE)
                killSnake(snake);
        }
    }

    private void checkAppleCollision(){
        List<Snake> tempSnakeCollection = new ArrayList<>(game.getSnakes());
        List<Apple> tempAppleCollection = new ArrayList<>(game.getApples());
        for(Snake snake : tempSnakeCollection){
            //Checks if snake eats an apple
            for(Apple apple : tempAppleCollection){
                if(snake.getHeadPosition().posEqual(apple.getPosition())){
                    snake.setLength(snake.getLength() + 1);
                    game.getApples().remove(apple);
                }
            }
        }
    }

    private void checkBattleCollision(){
        List<Snake> tempSnakeCollection = new ArrayList<>(game.getSnakes());
        for(Snake snake : tempSnakeCollection){
            //Check if two snakes collide
            for(Snake otherSnakes : tempSnakeCollection){

                //Checks if the two snakes are the same one
                if(snake.equals(otherSnakes)) continue;

                //Checks if the head of the snake collides with another snake head
                if(snake.getHeadPosition().posEqual(otherSnakes.getHeadPosition())){
                    if(snake.getLength() == otherSnakes.getLength()){
                        Random rand = new Random(2);
                        int winner = rand.nextInt();
                        if(winner == 0){
                            snake.setLength((int)(snake.getLength() + (otherSnakes.getLength() * 0.5)));
                            killSnake(otherSnakes);
                        }
                        else {
                            otherSnakes.setLength((int)(otherSnakes.getLength() + (snake.getLength() * 0.5)));
                            killSnake(snake);
                        }

                    }
                    //if snake is bigger than other snake, grow by 60% of other snake length
                    else if(snake.getLength() > otherSnakes.getLength()){
                        snake.setLength((int)(snake.getLength() + (otherSnakes.getLength() * 0.6)));
                        killSnake(otherSnakes);
                    }
                    else{
                        otherSnakes.setLength((int)(otherSnakes.getLength() + (snake.getLength() * 0.6)));
                        killSnake(snake);
                    }
                }
                //Checks if snake head collides with other snake body
                else if(Arrays.stream(otherSnakes.getBody()).anyMatch(i -> i.posEqual(snake.getHeadPosition()))){
                    for(int i = 1; i < otherSnakes.getLength(); ++i){
                        if(snake.getHeadPosition().posEqual(otherSnakes.getBody()[i])){
                            snake.setLength(snake.getLength() + (int)(0.5 * (otherSnakes.getLength() - i)));
                            otherSnakes.setLength(i+1);
                        }
                    }
                }
            }
        }
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
