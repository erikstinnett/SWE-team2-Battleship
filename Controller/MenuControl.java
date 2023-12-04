package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Server.GameClient;
import Utility.Feedback;

public class MenuControl implements ActionListener{

	private JPanel container;
	private GameClient gameClient;
	
	public MenuControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	public void setPanel(JPanel container) {
		this.container = container;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();

		if (action.equals("Exit")) {
			cardLayout.show(container, "InitialPanel");
			container.setName("InitialPanel");
		}
		else if (action.equals("Play!")) {
			Feedback feedback = new Feedback("Find a Game", "CreateGame");
			try {
				gameClient.sendToServer(feedback);
				cardLayout.show(container, "StartofGamePanel");
			}catch (Exception err) {
				err.printStackTrace();
			}
			System.out.println("Play! button pressed!");
			cardLayout.show(container, "StartofGamePanel");
			container.setName("StartofGamePanel");

		}
		else if (action.equals("View Scoreboard")) {
			String player_username = gameClient.getUsername();
			Feedback feedback = new Feedback(player_username, "ScoreBoardData");
			try {
				gameClient.sendToServer(feedback);
			}catch (Exception err) {
				err.printStackTrace();
			}

			cardLayout.show(container, "ScoreboardPanel");
		}
	}

}
