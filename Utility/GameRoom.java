package Utility;

import ocsf.server.ConnectionToClient;

public class GameRoom {
	private ConnectionToClient player1;
	private ConnectionToClient player2;
	private boolean full;

	//grids
	private ShipGrid p1_shipGrid;
	private ShootGrid p1_shootGrid;
	private ShipGrid p2_shipGrid;
	private ShipGrid p2_shootGrid;

	//latest shots
	private int[] p1_lastShot; 
	private int[] p2_lastShot;
	
	public GameRoom(ConnectionToClient p1) {
		this.player1 = p1;
	}
	
	public void setPlayer2(ConnectionToClient p2) {
		this.player2 = p2;
		full = true;
	}
	
	public ConnectionToClient getPlayer1() {
		return player1;
	}
	
	public ConnectionToClient getPlayer2() {
		return player2;
		
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

	public void setPlayer1LastShot(int[] lastShot){
		p1_lastShot = lastShot;
	}

	public int[] getPlayer1LastShot(){
		return p1_lastShot;
	}

	//player 2 boards
	public void setPlayer2Boards(ShipGrid shipGrid,ShootGrid shootGrid){
		p2_shipGrid = shipGrid;
		p2_shootGrid = shootGrid;
	}

	public ShipGrid getPlayer2ShipGrid(){
		return p2_shipGrid;
	}

	public void setPlayer2LastShot(int[] lastShot){
		p2_lastShot = lastShot;
	}

	public int[] getPlayer2LastShot(){
		return p2_lastShot;
	}
}
