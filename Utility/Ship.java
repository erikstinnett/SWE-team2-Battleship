package Utility;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.StartofGameControl;

public class Ship extends JPanel{
	
	private int size;
	private int lives;
	private boolean orientation;
	private int[] coordinates;
	private String name;
	private boolean status;
	private int id;
	private static final int CELL_SIZE = 55;
	private int lastRow = -1;
	private int lastCol = -1;
	
	public Ship(String name, Color color, StartofGameControl control) {
		this.name = name;
		switch (name){
		
			case "Carrier":
				size = 5;
				lives = 5;
				id = 20;
				break;
				
			case "Battleship":
				size = 4;
				lives = 4;
				id = 21;
				break;
				
			case "Cruiser":
				size = 3;
				lives = 3;
				id = 22;
				break;
				
			case "Submarine":
				size = 3;
				lives = 3;
				id = 23;
				break;
				
			case "Destroyer":
				size = 2;
				lives = 2;
				id = 24;
				break;
				
			default:
				break;
		}
		
		orientation = true;
		
		setPreferredSize(calculateDimension(size, orientation));
		setBackground(color);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		addMouseListener(control);
		addMouseMotionListener(control);
	}
	
	public static int getCELL_SIZE() {
		return CELL_SIZE;
	}
	
	public int getID() {
		return id;
	}
	
	public int[] getCoordinates() {
		return coordinates;
	}
	
	 public boolean isVertical() {
	 	return orientation;
	 }
	
	public String getName() {
		return name;
	}

	 public void toggleOrientation() {
	 	orientation = !orientation;
	 	setPreferredSize(calculateDimension(size, orientation));
	 	revalidate();
	 	repaint();
	 }
	
	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setStatus(boolean s) {
		this.status = s;
	}
	
	public boolean getStatus() {
		return status;
	}

	public void removeLife(){
		this.lives -= 1;
	}

	public boolean isSunk(){
		return lives == 0;
	}
	
	public int getShipSize() {
		return size;
	}
	
	public int getLastRow() {
		return lastRow;
	}
	
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	
	public int getLastCol() {
		return lastCol;
	}
	
	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}
	
	private Dimension calculateDimension(int shipSize, boolean isVertical) {
		return isVertical
				? new Dimension(CELL_SIZE, shipSize * CELL_SIZE)
				: new Dimension(shipSize * CELL_SIZE, CELL_SIZE);
	}
	
	public void setLastPosition(int row, int col) {
		this.lastRow = row;
		this.lastCol = col;
	}
	
}
 