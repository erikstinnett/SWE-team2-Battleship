package Controller;

import java.awt.*;
import javax.swing.*;

import Data.CreateAccountData;
import Panel.CreateAccountPanel;
import Server.GameClient;

import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener{

	private JPanel container;
	private GameClient gameClient;
	
	public CreateAccountControl(JPanel container, GameClient gameClient){
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Cancel")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "InitialPanel");
		}
		else if (action.equals("Submit")) {
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			String username = createAccountPanel.getUsername();
			String password = createAccountPanel.getPassword();
			String passwordVerify = createAccountPanel.getPasswordVerify();
			
			if (username.equals("") || password.equals("")) {
				createAccountPanel.setError("you must enter a username and password.");
				return;
			}
			else if (!password.equals(passwordVerify)) {
				createAccountPanel.setError("The two password did not match.");
				return;
			}
			if (password.length() < 6) {
				createAccountPanel.setError("The password must be at least 6 characters.");
				return;
			}
			
			CreateAccountData data = new CreateAccountData(username, password);
			try {
				gameClient.sendToServer(data);
				
			}catch (IOException err) {
				createAccountPanel.setError("Error connecting to the server.");
			}
		}
	}
	
	public void invalidAccount(String error){
		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		createAccountPanel.setError(error);
	}
	
	public void createAccountSuccess() {
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container,  "MenuPanel");
	}

}
