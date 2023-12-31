package ch.heigvd.gui;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.data.abstractions.VirtualClient;

import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    private final ClientStorage storage = ClientStorage.getInstance();
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public GuiPanelGame gamePanel;
    public GuiPanelMenu menuPanel;

    public GuiFrame(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuPanel = new GuiPanelMenu(this);
        gamePanel = new GuiPanelGame(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");

        this.add(mainPanel);

        //Set the title of the frame
        this.setTitle("Multiplayer Snake");
        //Set the default close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set if the frame can be resized
        this.setResizable(true);
        //Show panel and set size
        this.showMainPanel();
        //Set the visibility of the window
        this.setVisible(true);
        //Set the position of the window - this will position the window in the center
        this.setLocationRelativeTo(null);
        this.setBackground(Color.red);
    }

    public GuiPanelGame getGamePanel() {
        return gamePanel;
    }

    public GuiPanelMenu getMenuPanel() {
        return menuPanel;
    }

    //method to switch to the game panel
    public void showGamePanel(){
        cardLayout.show(mainPanel, "Game");
        gamePanel.grabFocus();
        storage.startUpdateEndpoint();
        pack();
    }

    public void showMainPanel(){
        storage.stopUpdateEndpoint();
        cardLayout.show(mainPanel, "Menu");
        pack();
    }
}
