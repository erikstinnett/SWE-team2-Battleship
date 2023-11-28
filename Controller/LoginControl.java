package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import Panel.LoginPanel;

public class LoginControl implements ActionListener{

	private JPanel container;
	private Object gameClient;
	
	public LoginControl(JPanel container, Object gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Cancel")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,"1");
		}
		else if (action.equals("Submit")) {
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			//LoginData data = new LoginData(loginPanel.getUsernamee(), loginPanel.getPassword());
			
//			if (data.getUsername().equals("") || data.getPassword.equals("")) {
//				loginPanel.setError("you must enter a username and password.");
//				return;
//			}
			
//			try {
//				client.sendToServer(data);
//			}
//			catch(IOException er) {
//				loginPanel.setError("Error connecting to the server.");
//			}
			System.out.println("SUBMIT HIT");
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
