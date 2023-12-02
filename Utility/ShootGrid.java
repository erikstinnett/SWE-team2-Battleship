package Utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ShootGrid extends Grid{

	public ShootGrid() {
		super();
		setLayout(new GridLayout(10, 10));
		initializeGrid();
		
	}
	
	public void initializeGrid() {
		JPanel[][] cells = new JPanel[10][10];
		int[][] grid = super.getGrid();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cells[i][j] = new JPanel();
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
						//nothing
						break;
				}
				add(cells[i][j]);
			}
		}
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
