package Utility;

public class ShootGrid extends Grid{

	private int[][] shootGrid;

	public ShootGrid() {
		super();
		
	}

	public void update(int[] lastShot){

		super.updateGrid(lastShot, "ShootGrid");
		//return parent grid
		// shootGrid = super.getGrid();
	}
	
	public int[][] getGrid(){
		return super.getGrid();
	}

}
