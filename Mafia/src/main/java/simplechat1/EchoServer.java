import common.ChatIF;
import ocsf.server.*;

import java.io.IOException;

public class EchoServer extends AbstractServer
{
  final public static int DEFAULT_PORT = 5555;
  ChatIF serverUI;

  public EchoServer(int port, ChatIF serverUI)
  {
    super(port);
    this.serverUI = serverUI;
  }

  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
    if(msg==null)
    {
      clientDisconnected(client);
    }
    else {
      System.out.println("Message received: " + msg + " from " + client);
      this.sendToAllClients(msg);
    }
  }

  public void handleMessageFromServerUI(String message) {
    try {
      if (isCommand(message) == true) {
        String[] messages = message.split(" ");

        if (message.equals("#quit")) {
          serverUI.display("Quitting Server.");
          this.sendToAllClients("Server is going to shut down");
          close();}

        else if (message.equals("#stop")) {
          serverUI.display("Server stopped from listening to new clients");
          stopListening();}

        else if (messages[0].equals("#close")){
          serverUI.display("Server is going to stop");
          close();}

        else if (messages[0].equals("#setport"))
          if (isListening() == false) setPort(Integer.parseInt(messages[1]));
          else serverUI.display("Cannot change port while server is on");

        else if (message.equals("#start"))
          if (isListening() == false) listen();
          else serverUI.display("Server is already listening");

        else if (message.equals("#getport"))
          serverUI.display(getPort() + "");

        else serverUI.display("Not a valid command!");
      } else {
        this.sendToAllClients(message);
        serverUI.display(message);
      }
    } catch (IOException e) {
      serverUI.display("Could not send message to server.  Terminating client.");
      try {
        close();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private boolean isCommand(String message) {
    if (message != null && message.length() > 0 && message.charAt(0) == '#') {
      return true;
    }
    return false;
  }

  protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }

  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }

  protected void clientConnected(ConnectionToClient client) {
    System.out.println("connected sucsesfuli");
    this.sendToAllClients("welcome here");
  }

  synchronized protected void clientDisconnected(ConnectionToClient client) {
    System.out.println("disconnected sucsesfulyyy");}
}
