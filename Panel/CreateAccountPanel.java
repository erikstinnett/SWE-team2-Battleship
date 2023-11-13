package Panel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class CreateAccountPanel extends JPanel{
  // Private data fields for the important GUI components.
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JPasswordField verifyPasswordField;

  private JLabel feedbackMessageLabel;
  
  // Getter for the text in the username field.
  public String getUsername()
  {
    return usernameField.getText();
  }
  
  // Getter for the text in the password field.
  public String getPassword()
  {
    return new String(passwordField.getPassword());
  }

  // Getter for password verification
  public String getPasswordForVerification(){
    return new String(verifyPasswordField.getPassword());
  }
  
  // Setter for the error text.
  public void setMsg(String msg)
  {
    feedbackMessageLabel.setText(msg);
    if (msg.equals("CreateAccountSuccessful")){
      

      feedbackMessageLabel.setForeground(Color.GREEN);
      usernameField.setText("");
      passwordField.setText("");
      verifyPasswordField.setText("");
    }
    else{
      feedbackMessageLabel.setForeground(Color.RED);
    }
    
  }
  
  // Constructor for the login panel.
  public CreateAccountPanel(CreateAccountControl cac)
  {
    // Create the controller and set it in the chat client.
    //CreateAccountControl controller = new CreateAccountControl(container, client);
    //client.setCreateAccountControl(controller);
        
    // Create a panel for the labels at the top of the GUI.
    JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    feedbackMessageLabel = new JLabel("", JLabel.CENTER);
    feedbackMessageLabel.setForeground(Color.RED);
    JLabel instructionLabel = new JLabel("Enter your username and password to create an account.", JLabel.CENTER);
    JLabel lengthInstructionLabel = new JLabel("Your password must be at least 6 characters.");
    labelPanel.add(feedbackMessageLabel);
    labelPanel.add(instructionLabel);
    labelPanel.add(lengthInstructionLabel);

    // Create a panel for the login information form.
    JPanel createAccountPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
    usernameField = new JTextField(10);
    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
    passwordField = new JPasswordField(10);
    JLabel verifyPasswordLabel = new JLabel("Verify Password",JLabel.RIGHT);
    verifyPasswordField = new JPasswordField(10);

    createAccountPanel.add(usernameLabel);
    createAccountPanel.add(usernameField);
    createAccountPanel.add(passwordLabel);
    createAccountPanel.add(passwordField);
    createAccountPanel.add(verifyPasswordLabel);
    createAccountPanel.add(verifyPasswordField);
    
    // Create a panel for the buttons.
    JPanel buttonPanel = new JPanel();
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(cac);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(cac);    
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    // Arrange the three panels in a grid.
    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
    grid.add(labelPanel);
    grid.add(createAccountPanel);
    grid.add(buttonPanel);
    this.add(grid);
  }
}
