package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MenuControl implements ActionListener{

	private JPanel container;
	private Object gameClient;
	
	public MenuControl(JPanel container, Object gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();

		if (action.equals("Exit")) {
			cardLayout.show(container, "InitialPanel");
		}
		else if (action.equals("Play!") || action.equals("Login!")) {
			// "Find Game"
			cardLayout.show(container, "StartofGamePanel");
		}
		else if (action.equals("View Scoreboard")) {
			// "View Scoreboard"
			cardLayout.show(container, "ScoreboardPanel");
		}
	}

}
