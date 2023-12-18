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
    private final int UNIT_SIZE = 25;
    private Game currentGame = null;

    public GuiPanelGame(){
        initBoard();
    }

    public void initBoard() {
        setBackground(Color.BLACK);
        setFocusable(true); //Need the focus to receive the command probably useless cause in manage elsewhere
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        addKeyListener(new MyKeyAdapter());
    }

    public void paintComponents(Graphics graphics){
        super.paintComponents(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){

        // Return if no game is set
        if(currentGame == null) return;

        //Draw lines to see the squares
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; ++i){
            graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            graphics.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        //Draw apples
        graphics.setColor(Color.red);
        for(int i = 0; i < currentGame.getApples().size(); ++i){
            graphics.drawOval(currentGame.getApples().get(i).getPosition().getX(),
                    currentGame.getApples().get(i).getPosition().getY(), UNIT_SIZE, UNIT_SIZE);
        }

        //Draw snakes
        for(int i = 0; i< currentGame.getSnakes().size(); ++i){
            ch.heigvd.data.models.Color snakeColor = currentGame.getSnakes().get(i).getColor();
            Color jPanelColor;
            switch(snakeColor){
                case Red -> jPanelColor = Color.red;
                case Blue -> jPanelColor = Color.blue;
                case Yellow -> jPanelColor = Color.yellow;
                case Green -> jPanelColor = Color.green;
                case Brown -> jPanelColor = new Color(153, 102, 0);
                default -> jPanelColor = Color.white;
            }
            graphics.setColor(jPanelColor);
            for(int j = 0; j < currentGame.getSnakes().get(i).getLength(); ++j){
                graphics.fillRect(currentGame.getSnakes().get(i).getBody()[j].getX(), currentGame.getSnakes().get(i).getBody()[i].getY(), UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    @Override
    public void handle(Command command) {
        if(command.getCommandType() != CommandType.UPDATE) return;
        currentGame = ((UpdateCommandData)command.getValue()).game();
        this.paintComponents(this.getGraphics());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

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
                        client.send(CommandFactory.getInputCommand(userId, Direction.LEFT));
                    }
                    catch (IOException ex) {
                        // Ignore
                    }
                });
            }
        }
    }
}
