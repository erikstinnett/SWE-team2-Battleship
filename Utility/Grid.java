package Utility;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class Grid extends JPanel{
	protected int[][] grid;
	protected JPanel[][] cells = new JPanel[10][10];
	
	public Grid() {
		grid = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cells[i][j] = new JPanel();
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}
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
	
	protected JPanel[][] getCells(){
		return cells;
	}

	protected String badPlacement(String err){

		return err;
	}

}
