package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import Data.EndofGameData;
import Panel.EndGamePanel;
import Server.GameClient;
import Utility.Feedback;

public class EndofGameControl implements ActionListener{
	JPanel container;
	GameClient gameClient;
	
	public EndofGameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();
		
		if (action.equals("Play Again")) {
			Feedback feedback = new Feedback("Find a Game", "CreateGame");
			try {
				gameClient.sendToServer(feedback);
				// cardLayout.show(container, "StartofGamePanel");
			}catch (Exception err) {
				err.printStackTrace();
			}
			System.out.println("Play! button pressed!");
			cardLayout.show(container, "StartofGamePanel");
		}
		else if (action.equals("Exit to Main Menu")) {
			cardLayout.show(container, "MenuPanel");
		}

		else if (action.equals("View Scoreboard")){
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
