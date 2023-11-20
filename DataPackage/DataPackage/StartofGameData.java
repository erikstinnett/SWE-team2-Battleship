package DataPackage;

import java.io.*;

public class StartofGameData {

	private int shipGrid;
	
	public int ShipGrid()
	  {
	    return shipGrid;
	  }
	
	public void getShipGrid(int shipGrid)
	  {
	    this.shipGrid = shipGrid;
	  }
	
	public StartofGameData(int shipGrid)
	{
		getShipGrid(shipGrid);
	}
	
}
