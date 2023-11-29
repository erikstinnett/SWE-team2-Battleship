package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import Data.LoginData;
import Panel.LoginPanel;
import Server.GameClient;

public class LoginControl implements ActionListener{

	private JPanel container;
	private GameClient gameClient;
	
	public LoginControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Cancel")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,"InitialPanel");
		}
		else if (action.equals("Submit")) {
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
			
			if (data.getUsername().equals("") || data.getPassword().equals("")) {
				loginPanel.setError("Error: You must enter a username and password.");
				return;
			}
			
			try {
				gameClient.sendToServer(data);
			}
			catch(IOException er) {
				loginPanel.setError("Error connecting to the server.");
			}
		}
	}
	
	public void loginSuccess() {
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "MenuPanel");
	}
	
	public void incorrectLogin(String error) {
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.setError(error);
	}

}
