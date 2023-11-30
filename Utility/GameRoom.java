package Utility;

import ocsf.server.ConnectionToClient;

public class GameRoom {

	//player related fields
	private ConnectionToClient player1;
	private ConnectionToClient player2;
	private boolean full;
	private String player1_username;
	private String player2_username;

	//grids
	private ShipGrid p1_shipGrid;
	private ShootGrid p1_shootGrid;
	private ShipGrid p2_shipGrid;
	private ShootGrid p2_shootGrid;

	//latest shots
	private int[] p1_lastShot; 
	private int[] p2_lastShot;
	
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

	// public void setPlayer1LastShot(int[] lastShot){
	// 	p1_lastShot = lastShot;
	// }

	// public int[] getPlayer1LastShot(){
	// 	return p1_lastShot;
	// }

	// public void updatePlayer1ShootGrid(int[] lastShot){
	// 	p1_shootGrid.update(lastShot);
	// }

	// public void updatePlayer1ShipGridWithShots(int[] lastShot,String type){
	// 	p2_shipGrid.update(lastShot,type);
	// }

	//player 2 boards
	public void setPlayer2Boards(ShipGrid shipGrid,ShootGrid shootGrid){
		p2_shipGrid = shipGrid;
		p2_shootGrid = shootGrid;
	}

	public ShipGrid getPlayer2ShipGrid(){
		return p2_shipGrid;
	}

	// public void setPlayer2LastShot(int[] lastShot){
	// 	p2_lastShot = lastShot;
	// }

	// public int[] getPlayer2LastShot(){
	// 	return p2_lastShot;
	// }

	// public void updatePlayer2ShootGrid(int[] lastShot){
	// 	p2_shootGrid.update(lastShot);
	// }

	// public void updatePlayer2ShipGridWithShots(int[] lastShot,String type){
	// 	p2_shipGrid.update(lastShot,type);
	// }

}
