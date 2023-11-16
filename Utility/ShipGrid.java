package Utility;

import java.util.ArrayList;

public class ShipGrid extends Grid{
	private ArrayList<Ship> ship;
	
	public ShipGrid(ArrayList<Ship> ship) {
		this.ship = ship;
	}

	public ArrayList<Ship> getShips() {
		return ship;
	}
}
