package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Panel.CreateAccountPanel;
import Panel.LoginPanel;

public class InitialControl implements ActionListener{
	
	private JPanel container;
	
	public InitialControl(JPanel container){
		this.container = container;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout)container.getLayout();
		if (action.equals("Login")) {
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			loginPanel.setError("");
			cardLayout.show(container,"2");
		}
		else if (action.equals("Create Account")) {
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			createAccountPanel.setError("");
			cardLayout.show(container,"3");
		}
	}

}
