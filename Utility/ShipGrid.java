package Utility;

import java.util.ArrayList;

public class ShipGrid extends Grid{
	private ArrayList<Ship> ships;
	
	public ShipGrid(ArrayList<Ship> ships) {
		super();
		this.ships = ships;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public int[][] getGridasArray(){
		return super.getGrid();
	}

	public void update(int[] lastShot, String type){
		//type = hit or miss
		super.updateGrid(lastShot, type);
	}
}
