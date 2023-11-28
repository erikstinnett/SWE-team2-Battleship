package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Data.EndofGameData;
import Data.GameData;
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
		}
	}
	
	public void fire(ShootGrid shootGrid, int[] target) {
		// build game data, send to server
	}
	
	public void updateGrids(GameData gameData) {
		// unpackage game data, and tell teh panel to update
	}
	
	public void endGame(EndofGameData endGameData) {
		// switch Panel views after updating end of game panel
	}

}
