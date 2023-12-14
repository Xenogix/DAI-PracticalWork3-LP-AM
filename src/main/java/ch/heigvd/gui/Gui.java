package ch.heigvd.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {

    //Board properties
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private final int SQUARE_SIZE = 10;
    private final int SQUARE_SUM = 900;

    public Gui(){
        initBoard();
    }

    public void initBoard() {
        setBackground(Color.BLACK);
        setFocusable(true); //Need the focus to receive the command probably useless cause in manage elsewhere
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
