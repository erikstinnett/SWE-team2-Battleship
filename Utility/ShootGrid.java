package Utility;

public class ShootGrid extends Grid{

	private int[][] shootGrid;

	public ShootGrid() {
		super();
		shootGrid = super.getGrid(); 
		
	}
	
	public void miss(int[] coordinate){
		
	}

	public void update(int[] lastShot){

		super.updateGrid(lastShot, "ShootGrid");
		//return parent grid
		// shootGrid = super.getGrid();
	}
	
	public int[][] getGrid(){
		return shootGrid;
	}

}
