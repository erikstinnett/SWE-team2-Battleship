package Utility;

public class Ship {
	
	// private int size;
	private int lives;
	// private boolean orientation;
	private int[] coordinates;
	private String name;
	private boolean status;
	private int id;
	
	public Ship(String name, int lives) {
		this.name = name;
		// this.size = size;
		// this.id = id;
		this.lives = lives;
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
 