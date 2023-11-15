package client;

import ocsf.client.*;
import common.*;
import java.io.*;

public class ChatClient extends AbstractClient {
    ChatIF clientUI;

    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
    }

    public void handleMessageFromServer(Object msg) {
        clientUI.display(msg.toString());
    }

    public void connectionException(Exception exception) {
        System.out.println("Serverul s-a deconectat");
        quit();
    }

    public void handleMessageFromClientUI(String message) {
        try {
            if (isCommand(message) == true) {
                String[] messages = message.split(" ");

                if (message.equals("#quit")) {
                    clientUI.display("Quitting Server.");
                    quit();}

                else if (message.equals("#logoff")) {
                    clientUI.display("Logging off");
                    closeConnection();}

                else if (messages[0].equals("#sethost"))
                    if (isConnected() == false) setHost(messages[1]);
                    else clientUI.display("Cannot change host while connected");

                else if (messages[0].equals("#setport"))
                    if (isConnected() == false) setPort(Integer.parseInt(messages[1]));
                    else clientUI.display("Cannot change port while connected");

                else if (message.equals("#login"))
                    if (isConnected() == false) openConnection();
                    else clientUI.display("Cannot log in while connected");

                else if (message.equals("#gethost"))
                    if (isConnected() == true) clientUI.display(getHost());
                    else clientUI.display("Client is not connected to a server");

                else if (message.equals("#getport"))
                    if (isConnected() == true) clientUI.display(getPort() + "");
                    else clientUI.display("Client is not connected to a server");

                else clientUI.display("Not a valid command!");
            } else {
                sendToServer(message);
            }
        } catch (IOException e) {
            clientUI.display("Could not send message to server.  Terminating client.");
            quit();
        }
    }

    private boolean isCommand(String message) {
        if (message != null && message.length() > 0 && message.charAt(0) == '#') {
            return true;
        }
        return false;
    }

    // This method terminates the client.
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }
}