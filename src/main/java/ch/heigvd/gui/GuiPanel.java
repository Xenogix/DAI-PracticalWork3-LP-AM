package ch.heigvd.gui;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
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
import java.util.Random;

public class GuiPanel extends JPanel implements ActionListener {

    //Board properties
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE = 25;
    private final int GAME_UNIT = (SCREEN_HEIGHT*SCREEN_WIDTH) /UNIT_SIZE;

    private final int DELAY = 75;

    Timer timer;
    Random random;
    private VirtualClient virtualClient;

    public GuiPanel(VirtualClient virtualClient){
        initBoard();
        this.virtualClient = virtualClient;
    }

    public void initBoard() {
        random = new Random();
        setBackground(Color.BLACK);
        setFocusable(true); //Need the focus to receive the command probably useless cause in manage elsewhere
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        addKeyListener(new MyKeyAdapter());

        //startGame();
    }

    public void paintComponents(Graphics graphics){
        super.paintComponents(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){

        //Draw lines to see the squares
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; ++i){
            graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            graphics.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        Game game = ClientStorage.getInstance().getGame();

        //Draw apples
        graphics.setColor(Color.red);
        for(int i = 0; i < game.getApples().size(); ++i){
            graphics.drawOval(game.getApples().get(i).getPosition().getX(),
                              game.getApples().get(i).getPosition().getY(), UNIT_SIZE, UNIT_SIZE);
        }

        //Draw snakes
        for(int i = 0; i< game.getSnakes().size(); ++i){
            ch.heigvd.data.models.Color snakeColor = game.getSnakes().get(i).getColor();
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
            for(int j = 0; j < game.getSnakes().get(i).getLength(); ++j){
                graphics.fillRect(game.getSnakes().get(i).getBody()[j].getX(), game.getSnakes().get(i).getBody()[i].getY(), UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        Direction direction;
        String userId = ClientStorage.getInstance().getUserId();
        Game game = ClientStorage.getInstance().getGame();

        //todo i dont understand why this doesnt work ...
        for(Snake snake : game.getSnakes()){
            if(snake.equals(userId))
                direction = snake.getDirection();
        }

        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if(!direction.equals(Direction.LEFT)){
                        try {
                            virtualClient.send(CommandFactory.getInputCommand(userId, Direction.LEFT));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(!direction.equals(Direction.RIGHT)){
                        try {
                            virtualClient.send(CommandFactory.getInputCommand(userId, Direction.RIGHT));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(!direction.equals(Direction.UP)){
                        try {
                            virtualClient.send(CommandFactory.getInputCommand(userId, Direction.UP));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(!direction.equals(Direction.DOWN)){
                        try {
                            virtualClient.send(CommandFactory.getInputCommand(userId, Direction.DOWN));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
            }
        }
    }
}
