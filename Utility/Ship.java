package Utility;

public class Ship {
	
	private int size;
	private boolean orientation;
	private int[] coordinate;
	private String name;
	private boolean status;
	private int id;
	
	public Ship(String name, int size, int id) {
		this.name = name;
		this.size = size;
		this.id = id;
	}
	
	public int[] getCoordinate() {
		return coordinate;
	}
	
	public boolean getOrientation() {
		return orientation;
	}
	
	public String getName() {
		return name;
	}
	public void setOrientation(boolean o) {
		this.orientation = o;
	}
	
	public void setCoordinate(int[] coordinate) {
		this.coordinate = coordinate;
	}
	
	public void setStatus(boolean s) {
		this.status = s;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public int getID() {
		return id;
	}

	
	
}
 