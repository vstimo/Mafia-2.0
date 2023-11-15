package simplechat1;

import simplechat1.client.*;
import simplechat1.common.*;

import java.io.*;

public class ClientConsole implements ChatIF
{
  final public static int DEFAULT_PORT = 5555;

  ChatClient client;

  public ClientConsole(String host, int port)
  {
    try
    {
      client= new ChatClient(host, port, this);
    }
    catch(IOException exception)
    {
      System.out.println("Error: Can't setup connection!" + " Terminating client.");
      System.exit(1);
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
        client.handleMessageFromClientUI(message); //mesaju pe care il scriu in Client
      }
    }
    catch (Exception ex)
    {
      System.out.println("Unexpected error while reading from console!");
    }
  }
  public void display(String message)
  {
    System.out.println("> " + message);
  }

  public static void main(String[] args)
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    try{
      port=Integer.parseInt(args[1]);
    }
    catch(ArrayIndexOutOfBoundsException e){
      port = DEFAULT_PORT;
    }
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();
  }
}
