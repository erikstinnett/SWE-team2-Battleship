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
import Panel.StartOfGamePanel;
import Server.GameClient;
import Utility.Feedback;
import Utility.Ship;
import Utility.ShipGrid;
import Utility.ShootGrid;

public class GameControl implements ActionListener{

	JPanel container;
	GameClient gameClient;
	GamePanel gamePanel;
	EndGamePanel eogPanel;
	
	//Utilities
	ShipGrid shipGrid;
	ShootGrid shootGrid;
	int[] shot;
	Feedback feedback;

	//Data
	GameData gameData;

	//Update player on their shot, turn, etc
	String status;
	String turn;

	public void setStatus(String status){
		this.status = status;
	}
	
	public GameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}

	public void setGameData(GameData gameData){
		this.gameData = gameData;
	}

	public void setGamePanel(GamePanel gp){
		this.gamePanel = gp;
	}

	public void setPlayerTurn(String turn){
		this.turn = turn;
	}

	// TO-DO figure out how turn order will be handled!
	public void endGame(String msg){
		CardLayout cardLayout = (CardLayout) container.getLayout();
		eogPanel = (EndGamePanel)container.getComponent(7);
		// gp.drawShip(sogPanel.getGrid());
		eogPanel.setResult(msg);
		// reset grids in case they play again
		gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.drawShip(new ShipGrid());
		gamePanel.drawShoot(new ShootGrid());
		cardLayout.show(container, "EndGamePanel");
	
		// reset grids in case they play again
//		gamePanel = (GamePanel)container.getComponent(5);
//		gamePanel.drawShip(new ShipGrid());
//		gamePanel.drawShoot(new ShootGrid());

		//reset sogPanel
		StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
		StartofGameControl sogControl = sogPanel.getController();
		sogControl.enableAllComponents();
		
		for (Ship i : sogPanel.getShips()) {
			i.setBounds(610, 10, i.getPreferredSize().width, i.getPreferredSize().height);
		}
		sogPanel.getGrid().setGridArray(new int [10][10]);
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

				// if (x >= 0 && x < 10 && y >= 0 && y < 10) {
					// shootAtGrid(x, y);
				shot = new int[2];
				shot[0] = x;
				shot[1] = y;
				//validate the shot
				shootGrid = this.gameData.getShootGrid();
				feedback = shootGrid.validate(shot);
				//get feedback message and display if invalidShot
				String shot_feedback = feedback.getMessage();
				//get type to see what kind of shot it was
				String shot_validity = feedback.getType();
				if (shot_validity.equals("invalidShot")){
					JOptionPane.showMessageDialog(gamePanel,shot_feedback);
				}
				else if (shot_validity.equals("validShot")){
					//since it's valid, assign it
					gameData.setTarget(shot);
					//set turn order (opponent next) and status (opponent turn)
					gamePanel.setTurnOrder(false,status);
					// send the data to the server
					gameData.setTurn(turn);
					gameClient.sendToServer(gameData);
				}					 
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gamePanel,"Invalid input format. Please enter coordinates in the format 'x,y'");
			}
		}
	}
	
	// public void fire(ShootGrid shootGrid, int[] target) {
	// 	// build game data, send to server
	// }

	//this is a key setter for initializing gamedata to communicate with the server
	public void updateGrids(GameData gameData, Boolean updateShipG, Boolean updateShootG) {
		// unpackage game data, and tell teh panel to update
		// this.gameData = gameData;
		gamePanel = (GamePanel)container.getComponent(5);

		// int nc = container.getComponentCount();
        // String numC = String.valueOf(nc);

        // System.out.println(numC);

		//get hit or miss
		int hit_or_miss = 0;
		if (gameData.getDetailedFeedback().equals("hit") || gameData.getDetailedFeedback().contains("Sunk"))
			hit_or_miss = 1;	
		else if (gameData.getDetailedFeedback().equals("miss"))
			hit_or_miss = 2;
		
		if (updateShipG){
			// gamePanel = (GamePanel)container.getComponent(10);
			// setShipGrid(gameData.getShipGrid());
			// int[] shot = gameData.getTarget();
			// //this classes shipgrid
			// shipGrid = this.gameData.getShipGrid();
			// shipGrid.placeShot(hit_or_miss, shot[0], shot[1]);
			this.gameData.setShipGrid(gameData.getShipGrid());
			//redraw grid
			gamePanel.drawShip(this.gameData.getShipGrid());
			//set turn order
			gamePanel.setTurnOrder(true, status);
		}
		if (updateShootG){
			// gamePanel = (GamePanel)container.getComponent(3);
			// setShootGrid(gameData.getShootGrid());
			int[] shot = gameData.getTarget();
			//this classes shipgrid
			shootGrid = this.gameData.getShootGrid();
			shootGrid.placeShot(hit_or_miss, shot[0], shot[1]);
			this.gameData.setShootGrid(shootGrid);

			//redraw grid
			gamePanel.drawShoot(shootGrid);
			if (gameData.getDetailedFeedback().contains("Sunk")) {
				JOptionPane.showMessageDialog(gamePanel, gameData.getDetailedFeedback(), "SHIP SUNK!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			gamePanel.setTurnOrder(false, status);
		}

		// //set shipgrid
		// setShipGrid(gameData.getShipGrid());
		// //set shootgrid
		// setShootGrid(gameData.getShootGrid());
		
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
		
		// reset grids in case they play again
		gamePanel = (GamePanel)container.getComponent(5);
		gamePanel.drawShip(new ShipGrid());
		gamePanel.drawShoot(new ShootGrid());

		//reset sogPanel
		StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
		StartofGameControl sogControl = sogPanel.getController();
		sogControl.enableAllComponents();

	}

	
	// //setter for the shipgrid
	// public void setShipGrid(ShipGrid shipGrid){
	// 	// this.shipGrid = shipGrid;
	// 	this.gameData.setShipGrid(shipGrid);
	// }
	// //setter for the shootgrid
	// public void setShootGrid(ShootGrid shootGrid){
	// 	// this.shootGrid = shootGrid;
	// 	this.gameData.setShootGrid(shootGrid);
	// }

}
