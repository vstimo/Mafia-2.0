package game.gui;

import game.connection.Client;
import game.gui.custom.CustomButton;
import game.PlayerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Lobby extends JFrame {
    private static final Color KAKI = new Color(174, 194, 198);
    private List<PlayerInfo> playersList = new ArrayList<>();
    private JLabel[] playerListLabel = new JLabel[16];
    public CustomButton submitButton, readyButton, refreshButton;
    private JTextField nameField;
    private String playerName = "";
    public Client client;
    public Lobby(Client client) {
        this.client=client;
        generateGraphicalInterface();
        buttonsManage();
    }

    private void generateGraphicalInterface(){
        getContentPane().setBackground(KAKI);
        setTitle("Mafia");
        setSize(600, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        /*
        T O P    P A N E L
        Contains a label with the title "Welcome to Mafia" which is positioned in the north part
        of the frame.
         */
        JLabel titleLabel = new JLabel("WELCOME TO MAFIA!");
        titleLabel.setFont(new Font("Finger Printed", Font.PLAIN, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(200, 200));
        add(titleLabel, BorderLayout.NORTH);

        /*
        L E F T   P A N E L
        Contains a label which indicates the area to type user's name.
        A text area where the user can type his desired name for the current game.
        Two button: -> Submit button will be pressed when user is decided regarding his name
                    -> Ready button will be pressed rather user is ready/ or not ready to enter the game
         */
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(KAKI);
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Papyrus", Font.PLAIN, 25));
        leftPanel.add(nameLabel);

        nameField = new JTextField(11);
        nameField.setPreferredSize(new Dimension(20, 25));
        nameField.setFont(new Font("Roboto", Font.PLAIN, 20));
        leftPanel.add(nameField);

        submitButton = new CustomButton("Submit", new Color(227, 221, 211), Color.DARK_GRAY);
        readyButton = new CustomButton("Ready", new Color(37, 43, 53), Color.WHITE);
        refreshButton = new CustomButton("Refresh", Color.BLACK, Color.WHITE);

        leftPanel.add(submitButton);
        leftPanel.add(readyButton);
        leftPanel.add(refreshButton);
        leftPanel.setPreferredSize(new Dimension(250, 150));

        /*
        R I G H T    P A N E L
        Contains a label called "Players".
        It also contains an array of JLabels with calls the method getListOfPlayers() from the server and then
        it shows on the screen.
         */
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel playerLabel = new JLabel("     PLAYERS");
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(250, 50));
        playerLabel.setFont(new Font("Romanum Est", Font.PLAIN, 35));
        rightPanel.add(playerLabel, BorderLayout.NORTH);

        for (int i = 0; i < 16; i++) { //initialize array of labels that contains the names of players and then get the player's names
            playerListLabel[i] = new JLabel();
            playerListLabel[i].setFont(new Font("Another Typewriter", Font.PLAIN, 20));
            playerListLabel[i].setText(" " + i);
            rightPanel.add(playerListLabel[i]);
        }
        getListOfPlayers();
        rightPanel.setBackground(KAKI);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        setVisible(true);
    }

    private void buttonsManage() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText();
                client.setUsername(playerName);
                submitButton.setEnabled(false);
                /*
                Aici voi avea o metoda gen sendToServer(playerList);
                Asta e o metoda care trimite la server lista de jucatori impreuna cu statusul acestora
                 */
            }
        });

        readyButton.addActionListener(new ActionListener() {
            boolean ready = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!playerName.equals("")) {
                    ready = !(ready);
                    for (PlayerInfo player : playersList) {
                        if (player.getUsername().equals(playerName))
                            player.setReady(ready);
                    }
                    /*Aici voi avea o metoda gen sendToServer(playerList);
                Asta e o metoda care trimite la server lista de jucatori impreuna cu statusul acestora
                 */
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.listenForMessage();
            }
        });
    }

    private void getListOfPlayers() {
        /*
        Aici voi avea o metoda getFromServer() unde mi se va returna playerList care inseamna
        cate un client-player
         */
        playersList = new ArrayList<>(); //asta va veni din metoda de mai sus
        for (PlayerInfo player : playersList) {
            for (int i = 0; i < 16; i++) {
                if (playerListLabel[i].getText().equals(player.getUsername() + "\n"))
                    if (player.isReady())
                        playerListLabel[i].setForeground(Color.GREEN);
                    else playerListLabel[i].setForeground(Color.BLACK);
            }
        }
    }
}
