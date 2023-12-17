package ch.heigvd.gui;

import ch.heigvd.data.abstractions.VirtualClient;

import javax.swing.*;

public class GuiFrame extends JFrame {

    public GuiFrame(VirtualClient virtualClient){
        //create panel
        GuiPanel panel = new GuiPanel(virtualClient);
        this.add(panel);

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

}
