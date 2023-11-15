package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameGUI extends JFrame {
    JPanel miniWindowsPanel;
    public static final Color DAY = new Color(136, 202, 252);
    public static final Color NIGHT = new Color(2, 41, 84);

    public InGameGUI() {
        setTitle("Mafia");
        setSize(600, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout()); // Main layout of the game
        miniWindowsPanel = new JPanel(new GridLayout(4, 4, 5, 5)); // Create a panel for the mini windows
        miniWindowsPanel.setPreferredSize(new Dimension(600,495));
        // Create 16 mini windows
        for (int i = 0; i < 16; i++) {
            JPanel miniWindow = new JPanel();
            miniWindow.setBackground(DAY);
            miniWindowsPanel.add(miniWindow);
        }

        JTextField textField = new JTextField();
        textField.setFont(new Font("Night Stalker", Font.BOLD, 25));

        // Create a chat text area
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false); // Set as uneditable
        chatArea.setFont(new Font("Night Stalker", Font.BOLD, 30));

        // Create a scroll pane for the chat text area
        JScrollPane scroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(600, 160));

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                chatArea.append(text + "\n");
                textField.setText("");
            }
        });

        // Add the components to the main panel
        add(miniWindowsPanel, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setDay() {
        Component[] components = miniWindowsPanel.getComponents();
        for (Component component : components) {
            component.setBackground(DAY);
        }
    }

    public void setNight() {
        Component[] components = miniWindowsPanel.getComponents();
        for (Component component : components) {
            component.setBackground(NIGHT);
        }
    }
}