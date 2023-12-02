package Utility;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ShipGrid extends Grid {
	private ArrayList<Ship> ships;
	private final int size = 10;
	
	public ShipGrid() {
		super();
		setLayout(new GridLayout(size, size)); // set layout for the grid
		initializeGrid();
		ships = new ArrayList<Ship>();
	}
	
	private void initializeGrid() {
		JPanel[][] cells = new JPanel[10][10];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cells[i][j] = new JPanel();
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(cells[i][j]);
			}
		}
	}
	
	public void placeShip(Ship ship, int rowStart, int colStart) {
		int shipSize = ship.getShipSize();
		boolean isVertical = ship.isVertical();
		int [][] grid = super.getGrid();
		
		// Check if the entire ship can be placed without going out of bounds
        if ((isVertical && (rowStart + shipSize) > size) || (!isVertical && (colStart + shipSize) > size)) {
            System.out.println("Ship placement is out of bounds.");
            return; // Ship placement is out of bounds
        }

        for (int i = 0; i < shipSize; i++) {
            int currentRow = isVertical ? rowStart + i : rowStart;
            int currentCol = isVertical ? colStart : colStart + i;
            
            if (!ships.contains(ship)) {
            	ships.add(ship);
            }
            
            grid[currentRow][currentCol] = ship.getID();
        }
        
        super.setGrid(grid);
	}
	
	public int getGridSize() {
		return size;
	}
	
	public void clearShip(Ship ship) {
		int[][] grid = super.getGrid();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == ship.getID()) {
                	for (int k = 0; k < ships.size(); k++) {
                		if (ships.get(k).getID() == ship.getID()) {
                			ships.remove(k);
                			break;
                		}
                	}
                    grid[row][col] = 0;
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
