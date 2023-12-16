package game.connection;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); //o lista pentru fiecare instanta a clientului
    private Socket socket;
    private BufferedReader bufferedReader; //used to read messages
    private BufferedWriter bufferedWriter; //used to send messages
    private String clientUsername = "";

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (clientUsername.equals("")) this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            getListOfPlayers();
            broadCastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public ArrayList<String> getListOfPlayers() {
        ArrayList<String> playerList = new ArrayList<String>();
        for (int i = 0; i < clientHandlers.size(); i++)
            playerList.add(clientHandlers.get(i).clientUsername);
        return playerList;
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadCastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadCastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (getListOfPlayers().size() != 0) {
                    clientHandler.bufferedWriter.write(getListOfPlayers().toString());
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }

                clientHandler.bufferedWriter.write(messageToSend);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadCastMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
