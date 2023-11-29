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
			try {
				Feedback feedback = new Feedback("start new game", "Create Game");
				gameClient.sendToServer(feedback);
				cardLayout.show(container, "StartOfGamePanel");
			}catch (IOException exc){
				System.out.println("Error occurred connecting to server");
				cardLayout.show(container, "MenuPanel");
			}
		}
		else if (action.equals("Exit to Main Menu")) {
			cardLayout.show(container, "MenuPanel");
		}
		
	}

}
