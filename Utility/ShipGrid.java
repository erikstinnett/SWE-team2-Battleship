package Utility;

import java.util.ArrayList;

public class ShipGrid extends Grid{
	private ArrayList<Ship> ships;
	
	public ShipGrid(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public int[][] getGridasArray(){
		return super.getGrid();
	}
}
