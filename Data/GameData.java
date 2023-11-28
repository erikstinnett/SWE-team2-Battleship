package Data;

import java.io.*;

import Utility.ShipGrid;
import Utility.ShootGrid;

public class GameData {

	private ShipGrid shipGrid;
	private ShootGrid shootGrid;
	private int[] target;
	
	public ShipGrid getShipGrid()
	{
		return shipGrid;
	}
	
	public ShootGrid getShootGrid()
	{
		return shootGrid;
	}
	
	public int[] getTarget()
	{
		return target;
	}
	
	public void setShipGrid(ShipGrid shipGrid)
	{
		this.shipGrid = shipGrid;
	}
			
	public void setShootGrid(ShootGrid shootGrid)
	{
		this.shootGrid = shootGrid;
	}
	
	public void setTarget(int[] target)
	{
		this.target = target;
	}
	
	public GameData(int shipGrid, int shootGrid)
	{
		setShipGrid(shipGrid);
		setShootGrid(shootGrid);
	}
}