package ch.heigvd.gui;

import ch.heigvd.data.abstractions.VirtualClient;

import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    private VirtualClient virtualClient;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GuiPanelGame gamePanel;
    private GuiPanelMenu menuPanel;


    public GuiFrame(VirtualClient virtualClient){
        this.virtualClient = virtualClient;

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuPanel = new GuiPanelMenu(this.virtualClient, this);
        gamePanel = new GuiPanelGame(this.virtualClient);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");

        this.add(mainPanel);

        //Set the title of the frame
        this.setTitle("Multiplayer Snake");
        //Set the default close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set if the frame can be resized
        this.setResizable(false);
        //
        this.pack();
        //Set the visibility of the window
        this.setVisible(true);
        //Set the position of the window - this will position the window in the center
        this.setLocationRelativeTo(null);
    }

    //method to switch to the game panel
    public void showGamePanel(){
        cardLayout.show(mainPanel, "Game");
    }

}
