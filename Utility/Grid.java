package Utility;

import javax.swing.JPanel;

public abstract class Grid extends JPanel{
	protected int[][] grid;
	
	public Grid() {
		grid = new int[10][10];
	}
	
	protected void setGrid(int[][] grid) {
		this.grid = grid;
	}
	
	protected void hit(int[] coord) {
		grid[coord[0]][coord[1]] = 1;
	}
	
	protected void miss(int[] coord) {
		grid[coord[0]][coord[1]] = 2;
	}
	
	protected int[][] getGrid(){
		return grid;
	}

	protected String badPlacement(String err){

		return err;
	}

}
