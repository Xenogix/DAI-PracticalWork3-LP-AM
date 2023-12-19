package ch.heigvd;

import ch.heigvd.client.net.ClientCommandSender;
import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.CommandHandler;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.CommandType;
import ch.heigvd.data.commands.data.AcceptCommandData;
import ch.heigvd.data.commands.data.UpdateCommandData;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.models.Direction;
import ch.heigvd.data.models.Game;
import ch.heigvd.data.models.Snake;
import ch.heigvd.gui.GuiFrame;

import javax.swing.*;
import java.util.Random;

public class MainClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new GuiFrame();
            frame.setVisible(true);
        });
    }
}
