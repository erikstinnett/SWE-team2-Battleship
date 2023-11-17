package Panel;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }

    // Additional methods and logic for login
    
    public LoginPanel(LoginControl lc)
    {
    	JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    	errorLabel = new JLabel("", JLabel.CENTER);
    	errorLabel.setForeground(Color.RED);
    	JLabel instructionLabel = new JLabel("Enter Username and Password", JLabel.CENTER);
    	
    	labelPanel.add(errorLabel);
    	labelPanel.add(instructionLabel);
    	
    	//Creating a panel for the login information
    	
    	JPanel loginPanel = new JPanel(new GridLayout(2,2,5,5));
    	JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
    	usernameField = new JTextField(10);
    	JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
    	passwordField = new JPasswordField(10);
    	
    	loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        
        //Create a panel for the buttons.
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(lc);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(lc);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
    	
     // Arrange the three panels in a grid.
        JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
        grid.add(labelPanel);
        grid.add(loginPanel);
        grid.add(buttonPanel);
        this.add(grid);
    }
}
