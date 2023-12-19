package ch.heigvd.gui;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.client.net.ClientCommandSender;
import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.Command;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.commands.CommandType;
import ch.heigvd.data.commands.data.AcceptCommandData;
import ch.heigvd.data.commands.data.JoinCommandData;
import ch.heigvd.data.models.Color;
import ch.heigvd.data.shared.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;


public class GuiPanelMenu extends JPanel {
    private final JTextField userName,multicastIP, serverIP, serverPort, multicastPort;
    private final JLabel userNameLabel, multicastIPLabel, serverIPLabel, serverPortLabel, multicastPortLabel;
    private final JComboBox<Color> colorSelector;
    private final JButton joinButton;
    private final GuiFrame guiFrame;
    private final ClientStorage storage = ClientStorage.getInstance();

    public GuiPanelMenu(GuiFrame guiFrame){

        this.guiFrame = guiFrame;

        //Set layout
        this.setSize(200, 400);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Input and labels
        userNameLabel = new JLabel("Username");
        userName = new JTextField(10);
        userNameLabel.setLabelFor(userName);
        serverIPLabel = new JLabel("Server address");
        serverIP = new JTextField(15);
        serverIPLabel.setLabelFor(serverIP);
        serverPortLabel = new JLabel("Server port");
        serverPort = new JTextField(Integer.toString(Constants.DEFAULT_SERVER_PORT), 5);
        serverPortLabel.setLabelFor(serverPort);
        multicastIPLabel = new JLabel("Multicast address");
        multicastIP = new JTextField(Constants.DEFAULT_UPDATE_ADDRESS, 15);
        multicastIPLabel.setLabelFor(multicastIP);
        multicastPortLabel = new JLabel("Multicast port");
        multicastPort = new JTextField(Integer.toString(Constants.DEFAULT_UPDATE_PORT), 5);
        multicastPortLabel.setLabelFor(multicastPort);

        //Add label and inputs
        add(userNameLabel);
        add(userName);
        add(serverIPLabel);
        add(serverIP);
        add(serverPortLabel);
        add(serverPort);
        add(multicastIPLabel);
        add(multicastIP);
        add(multicastPortLabel);
        add(multicastPort);

        //Color selector
        colorSelector = new JComboBox<>(Color.values());
        add(colorSelector);

        //Join button
        joinButton = new JButton("Join game !");
        joinButton.addActionListener(e -> joinGame());
        add(joinButton);
    }

    public void joinGame(){
        String multCastIP = multicastIP.getText().trim();
        String servIP = serverIP.getText().trim();

        try {
            int multPort = Integer.parseInt(multicastPort.getText().trim());
            int servPort = Integer.parseInt(serverPort.getText().trim());

            //virtual client set IPs and Ports for server and multicast
            storage.setUpdateEndpoint(new ClientUpdateEndpoint(multCastIP, multPort, guiFrame.getGamePanel()));
            storage.setVirtualClient(new ClientCommandSender(servIP, servPort));
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "The port should be a number between 1024 and 65535");
            return;
        }

        Color selectedColor = (Color)colorSelector.getSelectedItem();

        //Send command if a color is selected and there's a username
        if(selectedColor != null && !userName.getText().trim().isEmpty()){
            try {
                Command command = storage.getVirtualClient().send(CommandFactory.getJoinCommand(userName.getText().trim(), selectedColor));

                // Set user id
                if(command.getCommandType() == CommandType.ACCEPT)
                    storage.setUserId(((AcceptCommandData)command.getValue()).userId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        guiFrame.showGamePanel();
    }
}
