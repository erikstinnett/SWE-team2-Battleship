package Server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameServer extends AbstractServer
{
  private JTextArea log;
  private JLabel status;

  //private Database file = new Database();
  private Database db;
  private LoginData loginData;
  private CreateAccountData createAccountData;
  private User user_id;

  public void setDatabase(Database db){
    this.db = db;
  }
  
  public GameServer()
  {
    super(12345);
  }
  
  public GameServer(int port)
  {
    super(port);
  }
  
  public void setLog(JTextArea log)
  {
    this.log = log;
  }
  
  public JTextArea getLog()
  {
    return log;
  }
  
  public void setStatus(JLabel status)
  {
    this.status = status;
  }
  
  public JLabel getStatus()
  {
    return status;
  }
  
  
  
  @Override
  protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
  {
    // TODO Auto-generated method stub
    System.out.println("Message from Client " + arg0.toString() + arg1.toString());
    log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");



    // If we received LoginData, verify the account information.
    if (arg0 instanceof LoginData)
    {
      // Check the username and password with the database.
      LoginData data = (LoginData)arg0;
      Object result;
      String username = data.getUsername();
      String password = data.getPassword();
      String query = "select aes_decrypt(password,'key') from user where username='"+ username + "'"; 

      if (password.equals(db.queryCheckPassword(query)))
      {
        result = "LoginSuccessful";
        log.append("Client " + arg1.threadId() + " successfully logged in as " + data.getUsername() + "\n");
      }
      else
      {
        result = new Error("The username and password are incorrect.", "Login");
        log.append("Client " + arg1.threadId() + " failed to log in\n");
      }
      
      // Send the result to the client.
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
    }

    // If we received CreateAccountData, create a new account.
    else if (arg0 instanceof CreateAccountData)
    {
      // Try to create the account.
      CreateAccountData data = (CreateAccountData)arg0;
      Object result ="";
      String username = data.getUsername();
      String password = data.getPassword();
      String passwordForVerif = data.getPasswordForVerification();

      String query = "select username from user where username ='" + username + "'";

      //Error check
      Boolean NoCredError = true;

      if (username.equals("")||password.equals("")){
        result = new Error("You must enter a username and password", "CreateAccount");
        log.append("Client " + arg1.threadId() + " failed to create a new account\n");
        NoCredError = false;
      } 
      else if(username.length() < 6){
        result = new Error("Username must be at least 6 characters", "CreateAccount");
        log.append("Client " + arg1.threadId() + " failed to create a new account\n");
        NoCredError = false;
      }
      else if(!password.equals(passwordForVerif)){
        result = new Error("Passwords must match", "CreateAccount");
        log.append("Client " + arg1.threadId() + " failed to create a new account\n");
        NoCredError = false;
      }

      if (NoCredError) {
          if (!db.query(query)) //if NOT exists... create! (NoCredError has to be TRUE=no error)
        {
          //insert the username and password
          String dml = "insert into user values('";
                dml += username + "',aes_encrypt('";
                dml += password + "','key'))";
          
          // execute DML onto db
          try {
            db.executeDML(dml);
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

          result = "CreateAccountSuccessful";
          log.append("Client " + arg1.threadId() + " created a new account called " + data.getUsername() + "\n");
        }
        else
        {
          result = new Error("Username has already been selected", "CreateAccount");
          log.append("Client " + arg1.threadId() + " failed to create a new account\n");
        }
      }

      // Send the result to the client.
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
      
    }

  }
  
  protected void listeningException(Throwable exception) 
  {
    //Display info about the exception
    System.out.println("Listening Exception:" + exception);
    exception.printStackTrace();
    System.out.println(exception.getMessage());
    
    if (this.isListening())
    {
      log.append("Server not Listening\n");
      status.setText("Not Connected");
      status.setForeground(Color.RED);
    }
    
  }
  
  protected void serverStarted() 
  {
    System.out.println("Server Started");
    status.setText("Listening");
    status.setForeground(Color.GREEN);
    log.append("Server Started\n");
  }
  
  protected void serverStopped() 
  {
    System.out.println("Server Stopped");
    status.setText("Stopped");
    status.setForeground(Color.RED);
    log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
  }
  
  protected void serverClosed() 
  {
    System.out.println("Server and all current clients are closed - Press Listen to Restart");
    status.setText("Close");
    status.setForeground(Color.RED);
    log.append("Server and all current clients are closed - Press Listen to Restart\n");
  }

  
  protected void clientConnected(ConnectionToClient client) 
  {
    //Create User id
    Long id = client.threadId();
    String userID = id.toString();
    user_id = new User(userID);

    System.out.println("Client Connected");
    log.append("Client Connected\n");
  }
  
  
  


}
