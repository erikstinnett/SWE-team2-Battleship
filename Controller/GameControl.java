package Controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Data.EndofGameData;
import Data.GameData;
import Panel.EndGamePanel;
import Panel.GamePanel;
import Server.GameClient;
import Utility.ShipGrid;
import Utility.ShootGrid;

public class GameControl implements ActionListener{

	JPanel container;
	GameClient gameClient;
	
	public GameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Fire!")) {
			// locally validate, build the shoot grid, and call fire method to send request to server
			JTextField shipGuess;
			shipGuess = container.getShipGuess();
			String guess = shipGuess.getText();
			try {
				String[] parts = guess.split(",");
				int x = Integer.parseInt(parts[0].trim());
				int y = Integer.parseInt(parts[1].trim());

				if (x >= 0 && x < 10 && y >= 0 && y < 10) {
					// shootAtGrid(x, y);
					
					// send the coordinates to the server
					 
				} else {
					JOptionPane.showMessageDialog(container,"Invalid coordinates, please enter values between 0 and 9");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(container,"Invalid input format. Please enter coordinates in the format 'x,y'");
			}
		}
	}
	
	public void fire(ShootGrid shootGrid, int[] target) {
		// build game data, send to server
	}
	
	public void updateGrids(GameData gameData) {
		// unpackage game data, and tell teh panel to update
	}
	
	public void endGame(EndofGameData eogData) {
		EndGamePanel egp = (EndGamePanel) container.getComponent(9);
		if (eogData.isWin()) {
			egp.setResult("CONGRATULATIONS!!! YOU WIN!");
		}
		else {
			egp.setResult("You lose. Try again!");
		}
		
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "EndGamePanel");
	}

}
