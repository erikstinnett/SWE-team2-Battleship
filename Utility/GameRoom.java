package Utility;

import Data.GameData;
import ocsf.server.ConnectionToClient;

public class GameRoom {

	//player related fields
	private ConnectionToClient player1;
	private ConnectionToClient player2;
	private boolean full;
	private String player1_username = "";
	private String player2_username = "";

	//grids
	private ShipGrid p1_shipGrid;
	private ShootGrid p1_shootGrid;
	private ShipGrid p2_shipGrid;
	private ShootGrid p2_shootGrid;

	//data 
	private GameData gameData_p1;
	private GameData gameData_p2;

	public void setP1gameData(GameData gameData){
		this.gameData_p1 = gameData;
	}

	public GameData getP1gameData(){
		return this.gameData_p1;
	}

	public void setP2gameData(GameData gameData){
		this.gameData_p2 = gameData;
	}

	public GameData getP2gameData(){
		return this.gameData_p2;
	}
	
	public GameRoom(ConnectionToClient p1) {
		this.player1 = p1;
	}
	
	//player methods
	public void setPlayer2(ConnectionToClient p2) {
		this.player2 = p2;
		full = true;
	}
	
	public ConnectionToClient getPlayer1() {
		return player1;
	}

	public String getPlayer1Username(){
		return player1_username;
	}

	public void setPlayer1Username(String username){
		player1_username = username;
	}
	
	public ConnectionToClient getPlayer2() {
		return player2;
		
	}

	public String getPlayer2Username(){
		return player2_username;
	}

	public void setPlayer2Username(String username){
		player2_username = username;
	}
	
	public boolean isFull() {
		return full;
	}

	//player 1 boards
	public void setPlayer1Boards(ShipGrid shipGrid,ShootGrid shootGrid){
		p1_shipGrid = shipGrid;
		p1_shootGrid = shootGrid;
	}

	public ShipGrid getPlayer1ShipGrid(){
		return p1_shipGrid;
	}

	public void setPlayer1ShipGrid(ShipGrid shipGrid){
		this.p1_shipGrid = shipGrid;
	}

	//player 2 boards
	public void setPlayer2Boards(ShipGrid shipGrid,ShootGrid shootGrid){
		p2_shipGrid = shipGrid;
		p2_shootGrid = shootGrid;
	}

	public ShipGrid getPlayer2ShipGrid(){
		return p2_shipGrid;
	}

	public void setPlayer2ShipGrid(ShipGrid shipGrid){
		this.p2_shipGrid = shipGrid;
	}

}
