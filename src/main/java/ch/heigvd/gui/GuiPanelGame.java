package ch.heigvd.gui;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.CommandType;
import ch.heigvd.data.commands.data.UpdateCommandData;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GuiPanelGame extends JPanel implements ActionListener, CommandHandler {

    //Board properties
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final ClientStorage storage = ClientStorage.getInstance();
    private final GuiFrame guiFrame;
    private Game currentGame = null;
    private boolean hasSpawned = false;

    Timer timer;

    public GuiPanelGame(GuiFrame guiFrame){
        this.guiFrame = guiFrame;
        initBoard();
        //timer = new Timer(75, this);
    }

    private void initBoard() {
        setBackground(Color.BLACK);
        setFocusable(true); //Need the focus to receive the command probably useless cause in manage elsewhere
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.addKeyListener(new MyKeyAdapter());
    }

    private void update(Game game) {
        this.currentGame = game;

        boolean isPlayerInGame = isPlayerInGame();
        updateHasSpawned(isPlayerInGame);
        if(checkPlayerDead(isPlayerInGame)) return;

        paintComponents(this.getGraphics());
    }

    private void updateHasSpawned(boolean isPlayerInGame) {
        if(isPlayerInGame && !hasSpawned)
            hasSpawned = true;
    }

    private boolean checkPlayerDead(boolean isPlayerInGame) {

        // If the player has not been spawned or it's in the current game that means it's alive
        // Else this means the snake has been killed
        if(!hasSpawned || isPlayerInGame) return false;

        // Set spawn to false and return to main menu
        hasSpawned = false;
        JOptionPane.showMessageDialog(this, "You are dead !");
        guiFrame.showMainPanel();

        return true;
    }

    private boolean isPlayerInGame() {
        String userId = storage.getUserId();
        for (Snake snake : currentGame.getSnakes()) {
            if(snake.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public void paintComponents(Graphics graphics){
        super.paintComponents(graphics);
        draw(graphics);
    }

    private void draw(Graphics graphics){

        // Return if no game is set
        if(currentGame == null || !hasSpawned) return;

        // Clear previous rendering
        graphics.setColor(Color.white);
        graphics.fillRect(0,0, getWidth(), getHeight());

        // Calculate unit size
        int unitSize = Math.min(getHeight() / currentGame.getBoard().getHeight(), getWidth() / currentGame.getBoard().getWidth());

        // Draw Board
        graphics.setColor(Color.black);
        graphics.fillRect(0,0, currentGame.getBoard().getWidth() * unitSize, currentGame.getBoard().getWidth() * unitSize);

        //Draw lines to see the squares
        //for(int i = 0; i < currentGame.getBoard().getHeight()/unitSize; ++i){
        //    graphics.drawLine(i*unitSize, 0, i*unitSize, currentGame.getBoard().getHeight() * unitSize);
        //    graphics.drawLine(0, i*unitSize, currentGame.getBoard().getWidth() * unitSize, i*unitSize);
        //}

        //Draw apples
        graphics.setColor(Color.red);
        for(int i = 0; i < currentGame.getApples().size(); ++i){
            graphics.drawOval(currentGame.getApples().get(i).getPosition().getX() * unitSize,
                    currentGame.getApples().get(i).getPosition().getY() * unitSize, unitSize, unitSize);
        }

        //Draw snakes
        for(int i = 0; i< currentGame.getSnakes().size(); ++i){
            Color jPanelColor = getColor(i);
            graphics.setColor(jPanelColor);
            for(int j = 0; j < currentGame.getSnakes().get(i).getLength(); ++j){
                graphics.fillRect(currentGame.getSnakes().get(i).getBody()[j].getX() * unitSize,
                                     currentGame.getSnakes().get(i).getBody()[j].getY() * unitSize, unitSize, unitSize);
            }
        }
    }

    private Color getColor(int i) {
        ch.heigvd.data.models.Color snakeColor = currentGame.getSnakes().get(i).getColor();
        Color jPanelColor;
        switch(snakeColor){
            case RED -> jPanelColor = Color.red;
            case BLUE -> jPanelColor = Color.blue;
            case YELLOW -> jPanelColor = Color.yellow;
            case GREEN -> jPanelColor = Color.green;
            case BROWN -> jPanelColor = new Color(153, 102, 0);
            default -> jPanelColor = Color.white;
        }
        return jPanelColor;
    }

    @Override
    public void handle(Command command) {
        if(command.getCommandType() != CommandType.UPDATE) return;
        EventQueue.invokeLater(() -> {
            Game game = ((UpdateCommandData)command.getValue()).game();
            this.update(game);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {repaint();}

    public class MyKeyAdapter extends KeyAdapter {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private Future<?> updateTask;
        private final ClientStorage storage = ClientStorage.getInstance();

        @Override
        public void keyPressed(KeyEvent e){

            String userId = ClientStorage.getInstance().getUserId();
            Direction actualDirection = null;

            for(Snake snake : currentGame.getSnakes()){
                if(snake.getUserId().equals(userId))
                    actualDirection = snake.getDirection();
            }

            Direction selectedDirection = null;
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT -> selectedDirection = Direction.LEFT;
                case KeyEvent.VK_RIGHT -> selectedDirection = Direction.RIGHT;
                case KeyEvent.VK_UP -> selectedDirection = Direction.UP;
                case KeyEvent.VK_DOWN -> selectedDirection = Direction.DOWN;
                default -> { return; }
            }

            if(selectedDirection != actualDirection)
                sendDirection(selectedDirection);
        }

        public void sendDirection(Direction direction) {
            if (updateTask == null || updateTask.isDone()) {
                updateTask = executorService.submit(() -> {
                    try {
                        String userId = ClientStorage.getInstance().getUserId();
                        VirtualClient client = storage.getVirtualClient();
                        if(client == null) return;
                        client.send(CommandFactory.getInputCommand(userId, direction));
                    }
                    catch (IOException ex) {
                        // Ignore
                    }
                });
            }
        }
    }
}
