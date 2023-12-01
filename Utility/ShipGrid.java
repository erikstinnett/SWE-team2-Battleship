package Utility;

import java.util.ArrayList;

public class ShipGrid extends Grid{
	private ArrayList<Ship> ships;
	
	public ShipGrid(ArrayList<Ship> ships) {
		super();
		this.ships = ships;
		int[][] grid = super.getGrid();
		
		for(Ship i : ships) {
			int[] location = i.getCoordinates();
			boolean vertical = i.getOrientation();
			int id = i.getID();
			int size = i.getSize();
			if (vertical) {
				for (int j = 0; j < size; j++) {
					grid[location[0]][location[1] + j] = id;
				}
			}
			else {
				for (int j = 0; j < size; j++) {
					grid[location[0] + j][location[1]] = id;
				}
			}
		}
		
		super.setGrid(grid);
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public int[][] getGridasArray(){
		return super.getGrid();
	}

	public Feedback update(int[] lastShot){
		//type = hit or miss
		//super.updateGrid(lastShot, type);
		grid = super.getGrid();
		int whatThere = grid[lastShot[0]][lastShot[1]];
		Feedback feedback = new Feedback("","");
		
		switch (whatThere) {
				
			case 20:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 20) {
						i.removeLife();
						//see if sunk and provide detailed message
						if (i.isSunk())
							feedback.setDetailedMessage("Sunk Carrier");
					}
				}
					
				break;
				
			case 21:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 21) {
						i.removeLife();
						if (i.isSunk())
							feedback.setDetailedMessage("Sunk Battleship");
					}
				}
				break;
				
			case 22:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 22) {
						i.removeLife();
						if (i.isSunk())
							feedback.setDetailedMessage("Sunk Cruiser");
					}
				}
				break;
			
			case 23:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 23) {
						i.removeLife();
						if (i.isSunk())
							feedback.setDetailedMessage("Sunk Submarine");
					}
				}
				break;
				
			case 24:
				feedback.setMessage("You hit a ship!");
				feedback.setType("hit");
				super.hit(lastShot);
				for (Ship i : ships) {
					if (i.getID() == 24) {
						i.removeLife();
						if (i.isSunk())
							feedback.setDetailedMessage("Sunk Destroyer");
					}
				}
				break;
				
			default:
				feedback.setMessage("You missed!");
				feedback.setType("miss");
				super.miss(lastShot);
				break;
		}
		
		return feedback;
	}
}
