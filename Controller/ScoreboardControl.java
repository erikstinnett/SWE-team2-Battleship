package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Panel.ScoreboardPanel;
import Server.GameClient;

public class ScoreboardControl implements ActionListener{

	JPanel container;
	GameClient gameClient;
	
	public ScoreboardControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
		
	}
	
	public void buildLeaderboard(Object[][] users) {
		Object[][] leaderBoard = new Object[6][3];
		
		for (int i = 1; i < users.length; i++) {
			leaderBoard[i-1] = users[i];
		}
		leaderBoard[leaderBoard.length-1] = users[0];
		
		ScoreboardPanel sbp = (ScoreboardPanel) container.getComponent(8);
		sbp.setTable(leaderBoard, this);
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "ScoreboardPanel");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Exit")) {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "MenuPanel");
		}
	}

}
