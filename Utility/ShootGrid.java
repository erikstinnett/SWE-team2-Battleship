package Utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class ShootGrid extends Grid{

	public void placeShot(int type, int x, int y){
		int[][] grid = getGrid();
		grid[x][y] = type; //hit, miss
		super.setGrid(grid);
	}

	public ShootGrid() {
		super();
		setLayout(new GridLayout(10, 10));
		setUp();
		
	}
	
	private void setUp() {
		JPanel[][] cells = super.getCells();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j< 10; j++) {
				add(cells[i][j]);
			}
		}
	}
	
	public void initializeGrid() {
		JPanel[][] cells = super.getCells();
		int[][] grid = super.getGrid();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				switch (grid[i][j]) {
					case 1:
						//hit
						cells[i][j].setBackground(Color.RED);
						break;
					
					case 2:
						//miss
						cells[i][j].setBackground(Color.YELLOW);
						break;
						
					default:
						cells[i][j].setBackground(this.getBackground());
						break;
				}
			}
		}
	}
	
	public Feedback validate(int[] shot) {
		Feedback feedback = new Feedback("","");
		
		try {
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
			

		} catch (Exception e) {
			feedback.setMessage("Enter values between 0 and 9");
			feedback.setType("invalidShot");
			return feedback;
		}

		return feedback;
	}

	public int[][] getGrid(){
		return super.getGrid();
	}
	
	public void setGridArray(int[][] grid) {
		super.setGrid(grid);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Add the numbers outside the grid here
        for (int i = 0; i < 10; i++) {
            // Draw row numbers on the left side
            g.drawString(Integer.toString(i), 0, i * (this.getHeight() / 10) + (this.getHeight() / 10 / 2));
            // Draw column numbers on the top
            g.drawString(Integer.toString(i), i * (this.getWidth() / 10) + (this.getWidth() / 10 / 2), 10);
        }
    }

}
