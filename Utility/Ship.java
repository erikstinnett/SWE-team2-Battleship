package Utility;

public class Ship {
	
	private int size;
	private int lives;
	private boolean orientation;
	private int[] coordinates;
	private String name;
	private boolean status;
	private int id;
	
	public Ship(String name, int lives) {
		this.name = name;
		switch (name){
		
			case "Carrier":
				size = 5;
				id = 20;
				break;
				
			case "Battleship":
				size = 4;
				id = 21;
				break;
				
			case "Cruiser":
				size = 3;
				id = 22;
				break;
				
			case "Submarine":
				size = 3;
				id = 23;
				break;
				
			case "Destroyer":
				size = 2;
				id = 24;
				break;
		}
		this.lives = lives;
	}
	
	public int getID() {
		return id;
	}
	
	public int[] getCoordinates() {
		return coordinates;
	}
	
	// public boolean getOrientation() {
	// 	return orientation;
	// }
	
	public String getName() {
		return name;
	}

	// public void setOrientation(boolean o) {
	// 	this.orientation = o;
	// }
	
	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setStatus(boolean s) {
		this.status = s;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	// public int getID() {
	// 	return id;
	// }

	public void removeLife(){
		this.lives -= 1;
	}

	public boolean isSunk(){
		if (lives == 0)
			return true;
		else
			return false;
	}
	
	
}
 