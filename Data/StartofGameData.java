package Data;

import java.io.*;

import Utility.ShipGrid;
import Utility.ShootGrid;

public class StartofGameData {

	// private int shipGrid;

	private ShipGrid shipGrid;
	private ShootGrid shootGrid;
	
	public ShipGrid getShipGrid()
	{
	    return shipGrid;
	}
	
	public ShootGrid getShootGrid(){
		return shootGrid;
	}
	
	public StartofGameData(ShipGrid shipGrid, ShootGrid shootGrid)
	{
		this.shipGrid = shipGrid;
		this.shootGrid = shootGrid;
	}
	
}
