import java.io.*;
import common.*;

public class ServerConsole implements ChatIF
{
    final public static int DEFAULT_PORT = 5555;
    EchoServer server;

    public ServerConsole(int port)
    {
        server= new EchoServer(port, this);
        try
        {
            server.listen(); //Start listening for connections
        }
        catch (Exception ex)
        {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
    public void accept()
    {
        try
        {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true)
            {
                message = fromConsole.readLine();
                server.handleMessageFromServerUI(message);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Unexpected error while reading from console!");
        }
    }
    public void display(String message)
    {
        System.out.println("SERVER MSG> " + message);
    }

    public static void main(String[] args)
    {
        int port = 0;  //The port number

        try{
            port=Integer.parseInt(args[1]);
        }
        catch(ArrayIndexOutOfBoundsException e){
            port = DEFAULT_PORT;
        }
        ServerConsole chat= new ServerConsole(port);
        chat.accept();  //Wait for console data
    }
}
