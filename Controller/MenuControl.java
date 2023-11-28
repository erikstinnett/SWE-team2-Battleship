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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();

		if (action.equals("Exit")) {
			cardLayout.show(container, "InitialPanel");
		}
		else if (action.equals("Play!") || action.equals("Login!")) {
			Feedback feedback = new Feedback("Find a Game", "Create Game");
			try {
				gameClient.sendToServer(feedback);
				cardLayout.show(container, "StartofGamePanel");
			}catch (Exception err) {
				err.printStackTrace();
			}
		}
		else if (action.equals("View Scoreboard")) {
			Feedback feedback = new Feedback("Show the leaderboards", "ScoreBoardData");
			try {
				gameClient.sendToServer(feedback);
				cardLayout.show(container, "ScoreboardPanel");
			}catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

}
