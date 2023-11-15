package gui;

import org.gui.custom.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame {
    private static final Color KAKI = new Color(174, 194, 198);
    public String playerName = "";
    public JLabel[] playersList;
    private int apasariReady = 0;

    public Lobby() {
        getContentPane().setBackground(KAKI);
        setTitle("Mafia");
        setSize(600, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Title at the top
        JLabel titleLabel = new JLabel("WELCOME TO MAFIA!");
        titleLabel.setFont(new Font("Finger Printed", Font.PLAIN, 40));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(200, 200));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for left side (Name field and Submit button)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(KAKI);
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Papyrus", Font.PLAIN, 25));

        JTextField nameField = new JTextField(11);
        nameField.setPreferredSize(new Dimension(20, 25));
        nameField.setFont(new Font("Roboto", Font.PLAIN, 20));
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);

        CustomButton submitButton = new CustomButton("Submit", new Color(227, 221, 211), Color.DARK_GRAY);
        CustomButton readyButton = new CustomButton("Ready", new Color(37, 43, 53), Color.WHITE);
        CustomButton exitButton = new CustomButton("Exit", new Color(0, 0, 0), Color.WHITE);

        leftPanel.add(submitButton);
        leftPanel.add(readyButton);
        leftPanel.add(exitButton);
        leftPanel.setPreferredSize(new Dimension(250, 150));

//////////// Panel for right side (Player list)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel playerLabel = new JLabel("     PLAYERS");
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setPreferredSize(new Dimension(250, 50));
        playerLabel.setFont(new Font("Romanum Est", Font.PLAIN, 35));
        rightPanel.add(playerLabel, BorderLayout.NORTH);

        playersList = new JLabel[16]; //initializez vectorul de label-uri iar apoi obtin lista de playeri deja existenta
        for (int i = 0; i < 16; i++) {
            playersList[i] = new JLabel();
            playersList[i].setFont(new Font("Another Typewriter", Font.PLAIN, 20));
            playersList[i].setText(" ");
            rightPanel.add(playersList[i]);
        }
        getListOfPlayers();
        rightPanel.setBackground(KAKI);

        //Now implementing ActionListener for each button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerName.equals("")) {
                    playerName = nameField.getText();
                    playersList[0].setText(playerName + "\n"); //linia asta dispare

                    //add player to player list to connectionToServer method list of players !!!!!!!
                    //add status that he is not ready yet
                }
            }
        });

        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apasariReady++;
                if (!playerName.equals("")) {
                    for (int i = 0; i < 16; i++) {
                        if (playersList[i].getText().equals(playerName + "\n"))
                            if (apasariReady % 2 == 1)
                                playersList[i].setForeground(Color.GREEN);
                                //change status that he is ready
                            else playersList[i].setForeground(Color.BLACK);
                            //change status that he is not ready!
                    }
                }
            }
        });


        /*
                    String tot = playerListArea.getText();
                    String[] parts = tot.split(playerName);
                    playerListArea.setText("");
                    playerListArea.setText(parts[0]);
                    p;

                }

                String[] players = playerListArea.getText().split("\n");
                if (players.length > 0) {
                    players[players.length - 1] = "<html><font color='green'>" + players[players.length - 1] + "</font></html>";
                }
                playerListArea.setText(String.join("\n", players));
            }
        });

        // ActionListener for Ready button


        // ActionListener for Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adjusting the sizes
        playerListArea.setPreferredSize(new Dimension(150, 200));
        readyButton.setPreferredSize(new Dimension(70, 30));
        exitButton.setPreferredSize(new Dimension(70, 30));

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(readyButton);
        buttonPanel.add(exitButton);

        // Add components to the frame


        add(buttonPanel, BorderLayout.CENTER);
*/
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        setVisible(true);
    }

    private void getListOfPlayers() {
        //here we get the list of players connected from the server
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lobby::new);
    }
}