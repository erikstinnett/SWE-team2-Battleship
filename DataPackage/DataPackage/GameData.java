package DataPackage;

import java.io.*;

public class GameData {

	private int shipGrid;
	private int shootGrid;
	private int[] target;
	
	public int getShipGrid()
	{
		return shipGrid;
	}
	
	public int getShootGrid()
	{
		return shootGrid;
	}
	
	public int[] getTarget()
	{
		return target;
	}
	
	public void setShipGrid(int shipGrid)
	{
		this.shipGrid = shipGrid;
	}
			
	public void setShootGrid(int shootGrid)
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
