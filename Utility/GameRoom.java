package Utility;

import ocsf.server.ConnectionToClient;

public class GameRoom {
	private ConnectionToClient player1;
	private ConnectionToClient player2;
	private boolean full;
	
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
}
