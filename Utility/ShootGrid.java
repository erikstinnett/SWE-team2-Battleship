package Utility;

public class ShootGrid extends Grid{

	// private int[][] shootGrid;

	public ShootGrid() {
		super();
		
	}
	
	public Feedback validate(int[] shot) {
		Feedback feedback = new Feedback("","");
		
		grid = super.getGrid();
		int WhatThere = grid[shot[0]][shot[1]];
		
		switch (WhatThere) {
			case 1:
				feedback.setMessage("This was already hit");
				feedback.setType("invalidShot");
				break;
				
			case 2:
				feedback.setMessage("This was already missed");
				feedback.setType("invalidShot");
				break;
								
			default:
				feedback.setMessage("This is a valid shot");
				feedback.setType("validShot");
				break;
		}
		return feedback;
	}

//	public void update(int[] lastShot){
//
//		super.updateGrid(lastShot, "ShootGrid");
//		//return parent grid
//		// shootGrid = super.getGrid();
//	}
//	
	public int[][] getGrid(){
		return super.getGrid();
	}

}
