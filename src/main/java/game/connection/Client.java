package game.connection;

import game.gui.Lobby;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public String username = "", playerList;
    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Nu s-a putut realiza instantierea clientului!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String messageToSend) {
        try {
            if (socket.isConnected()) {
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Nu s-a putut trimite mesajul de la clientul " + username + "!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        String messageFromChat;
        if (socket.isConnected()) {
            try {
                this.playerList = bufferedReader.readLine();
                messageFromChat = bufferedReader.readLine();
                System.out.println(messageFromChat);
            } catch (IOException e) {
                System.out.println("Nu s-a putut asculta pentru mesaje");
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

    public String getPlayerList(){
        return playerList;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setUsername(String name) {
        username = name;
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        Lobby room = new Lobby(client);
        room.refreshLobby();
    }
}
