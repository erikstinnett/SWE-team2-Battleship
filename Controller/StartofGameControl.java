package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Panel.StartOfGamePanel;
import Server.GameClient;

public class StartofGameControl implements ActionListener{

	JPanel container;
	GameClient gameClient;
	
	public StartofGameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	// TO-DO figure out how turn order will be handled!
	public void startGame(Boolean goesFirst){
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "GamePanel");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();
		
		if (action.equals("Confirm Ship Placement")) {
			StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
			sogPanel.setButtonStatus(false);
		}
	}

}
