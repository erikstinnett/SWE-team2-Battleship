package Data;

import java.io.*;

import Utility.ShipGrid;
import Utility.ShootGrid;

public class StartofGameData implements Serializable{

	// private int shipGrid;

	private ShipGrid shipGrid;
	private ShootGrid shootGrid;

	//player cred
	private String player_username = "";

	public void setPlayerUsername(String player_username){
		this.player_username = player_username;
	}

	public String getPlayerUsername(){
		return this.player_username;
	}
		
	public ShipGrid getShipGrid()
	{
	    return shipGrid;
	}
	
	public ShootGrid getShootGrid(){
		return shootGrid;
	}
	
	public void setShootGrid(ShootGrid shootGrid) {
		this.shootGrid = shootGrid;
	}
	
	public StartofGameData(ShipGrid shipGrid)
	{
		this.shipGrid = shipGrid;
		shootGrid = new ShootGrid();
	}
	
}
