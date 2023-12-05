package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
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
			feedback.setDetailedMessage(gameClient.getUsername());
			try {
				gameClient.sendToServer(feedback);
				cardLayout.show(container, "StartofGamePanel");
			}catch (Exception err) {
				JOptionPane.showMessageDialog(container.getComponent(3), "Could not connect to server. Exit the application and try again", "Server Connection Error", JOptionPane.ERROR_MESSAGE);

			}
			
			cardLayout.show(container, "StartofGamePanel");
			container.setName("StartofGamePanel");

		}
		else if (action.equals("View Scoreboard")) {
			String player_username = gameClient.getUsername();
			Feedback feedback = new Feedback(player_username, "ScoreBoardData");
			try {
				gameClient.sendToServer(feedback);
			}catch (Exception err) {
				JOptionPane.showMessageDialog(container.getComponent(3), "Could not connect to server. Exit the application and try again", "Server Connection Error", JOptionPane.ERROR_MESSAGE);

			}

//			cardLayout.show(container, "ScoreboardPanel");
		}
	}
	
	public void showScreen() {
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "ScoreboardPanel");
	}

}
