package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

			Feedback feedback1 = new Feedback("Find a Game", "CreateGame");
			try {
				gameClient.sendToServer(feedback1);
				// cardLayout.show(container, "StartofGamePanel");
			}catch (Exception err) {
				JOptionPane.showMessageDialog(container.getComponent(7), "Could not connect to server. Exit the application and try again", "Server Connection Error", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(container.getComponent(7), "Could not connect to server. Exit the application and try again", "Server Connection Error", JOptionPane.ERROR_MESSAGE);
			}

			cardLayout.show(container, "ScoreboardPanel");

		}
		
	}

}
