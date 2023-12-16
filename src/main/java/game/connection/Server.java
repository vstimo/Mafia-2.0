package game.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    ClientHandler clientHandler;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) { //cat timp serverul este deschis asteptam
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

//ceasdadsasd
            }
        } catch (IOException e) {
            System.out.println("S-a intrerupt conexiunea catre server");
            closeServerSocket();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2809);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
