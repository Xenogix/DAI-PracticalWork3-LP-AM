package ch.heigvd.gui;

import ch.heigvd.client.ClientStorage;
import ch.heigvd.client.net.ClientCommandSender;
import ch.heigvd.client.net.ClientUpdateEndpoint;
import ch.heigvd.data.abstractions.VirtualClient;
import ch.heigvd.data.commands.CommandFactory;
import ch.heigvd.data.models.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;


public class GuiPanelMenu extends JPanel {

    private final JTextField userName,multicastIP, serverIP, serverPort, multicastPort;
    private final JComboBox<Color> colorSelector;
    private final JButton joinButton;
    private final GuiFrame guiFrame;
    private final ClientStorage storage = ClientStorage.getInstance();

    public GuiPanelMenu(GuiFrame guiFrame){
        this.guiFrame = guiFrame;

        setLayout(new GridLayout(4,1));

        //Server and multicast IPs
        userName = new JTextField("Username");
        serverIP = new JTextField("IP server");
        multicastIP = new JTextField("IP multicat");
        serverPort = new JTextField("Server port");
        multicastPort = new JTextField("Multicast port");
        add(serverIP);
        add(serverPort);
        add(multicastIP);
        add(multicastPort);

        //Color selector
        colorSelector = new JComboBox<>(Color.values());
        add(colorSelector);

        // Join button
        joinButton = new JButton("Rejoindre la partie");
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGame();
            }
        });
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
                storage.getVirtualClient().send(CommandFactory.getJoinCommand(userName.getText().trim(), selectedColor));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        guiFrame.showGamePanel();
    }
}
