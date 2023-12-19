package ch.heigvd;

import ch.heigvd.gui.GuiFrame;

import javax.swing.*;

public class MainClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GuiFrame();
            frame.setVisible(true);
        });
    }
}
