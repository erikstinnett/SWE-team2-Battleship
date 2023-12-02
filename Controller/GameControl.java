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
import Utility.Feedback;
import Utility.ShipGrid;
import Utility.ShootGrid;

public class GameControl implements ActionListener{

	JPanel container;
	GameClient gameClient;
	GamePanel gamePanel;
	
	//Utilities
	ShipGrid shipGrid;
	ShootGrid shootGrid;
	int[] shot;
	Feedback feedback;

	//Data
	GameData gameData;
	
	public GameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
//		CardLayout cardLayout = (CardLayout)container.getLayout();
		gamePanel = (GamePanel)container.getComponent(5); //REVISE : might need to change this component 
		
		if (action.equals("Fire!")) {
			// locally validate, build the shoot grid, and call fire method to send request to server
			JTextField shipGuess;
			shipGuess = gamePanel.getShipGuess();
			String guess = shipGuess.getText();
			try {
				String[] parts = guess.split(",");
				int x = Integer.parseInt(parts[0].trim());
				int y = Integer.parseInt(parts[1].trim());

				if (x >= 0 && x < 10 && y >= 0 && y < 10) {
					// shootAtGrid(x, y);
					shot = new int[2];
					shot[0] = x;
					shot[1] = y;
					//validate the shot
					shootGrid = gamePanel.getShootGrid();
					feedback = shootGrid.validate(shot);
					//get feedback message and display
					String shot_feedback = feedback.getMessage();
					//get type to see what kind of shot it was
					String shot_validity = feedback.getType();
					if (shot_validity.equals("invalidShot")){
						//display shot_feedback
					}
					else if (shot_validity.equals("validShot")){
						// send the data to the server
//						gameClient.sendToServer(gameData);
						gamePanel.setButtonStatus(false);
					}					 
				} else {
					JOptionPane.showMessageDialog(gamePanel,"Invalid coordinates, please enter values between 0 and 9");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gamePanel,"Invalid input format. Please enter coordinates in the format 'x,y'");
			}
		}
	}
	
	public void fire(ShootGrid shootGrid, int[] target) {
		// build game data, send to server
	}

	//this is a key setter for initializing gamedata to communicate with the server
	public void updateGrids(GameData gameData) {
		// unpackage game data, and tell teh panel to update
		this.gameData = gameData;
		//set shipgrid
		setShipGrid(gameData.getShipGrid());
		//set shootgrid
		setShootGrid(gameData.getShootGrid());

		//Perform the update on the grids
		
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

	
	//setter for the shipgrid
	public void setShipGrid(ShipGrid shipGrid){
		this.shipGrid = shipGrid;
	}
	//setter for the shootgrid
	public void setShootGrid(ShootGrid shootGrid){
		this.shootGrid = shootGrid;
	}

}
