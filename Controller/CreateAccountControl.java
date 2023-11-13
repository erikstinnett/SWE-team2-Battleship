package Controller;

import java.awt.*;


import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener{
    // Private data fields for the container and chat client.
  private JPanel container;
  private GameClient chatClient;
  
  
  // Constructor for the login controller.
  public CreateAccountControl(JPanel container, GameClient chatClient)
  {
    this.container = container;
    this.chatClient = chatClient;
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
      CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword(), createAccountPanel.getPasswordForVerification());

      // Submit the login information to the server.
      try {
        chatClient.sendToServer(data);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     
    }
  }

  // Successful account creation
  public void accountCreationSuccess()
  {
    CardLayout cardLayout = (CardLayout)container.getLayout();
    cardLayout.show(container,"2");
    
  }

  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayMessage(String msg)
  {
    CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
    createAccountPanel.setMsg(msg);
    
  }
}
