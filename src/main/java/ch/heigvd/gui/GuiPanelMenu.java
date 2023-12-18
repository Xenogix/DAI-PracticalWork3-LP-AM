package ch.heigvd.gui;

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

    private JTextField multicastIP;
    private JTextField serverIP;
    private JComboBox<String> colorSelector;
    private JButton joinButton;
    private VirtualClient virtualClient;
    private GuiFrame guiFrame;

    public GuiPanelMenu(VirtualClient virtualClient, GuiFrame guiFrame){
        this.virtualClient = virtualClient;
        this.guiFrame = guiFrame;

        setLayout(new GridLayout(4,1));

        //Server and multicast IPs
        serverIP = new JTextField("IP server");
        multicastIP = new JTextField("IP multicat");
        add(serverIP);
        add(multicastIP);

        //Color selector
        String[] colors = {"Yellow", "Red", "Blue", "Green", "Brown"};
        colorSelector = new JComboBox<>(colors);
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
        String selectedColor = (String) colorSelector.getSelectedItem();
        Color color = null;

        switch(Objects.requireNonNull(selectedColor)){
            case "Red" -> color = Color.Red;
            case "Blue" -> color = Color.Blue;
            case "Yellow" -> color = Color.Yellow;
            case "Green" -> color = Color.Green;
            case "Brown" -> color = Color.Brown;
        }

        //todo get the right number for the multicast ip and server ip
        if(servIP.equals("1.1.1.1") && multCastIP.equals("1.1.1.1") && color != null){
            try {
                virtualClient.send(CommandFactory.getJoinCommand("test", color));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
