package Utility;

public abstract class Grid {
	private int[][] grid;
	
	public Grid() {
		grid = new int[10][10];
	}
	
	public void hit(int[] coord) {
		
	}
	
	public int[][] getGrid(){
		return grid;
	}
}
