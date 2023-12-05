package Utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ShipGrid extends Grid {
	private ArrayList<Ship> ships;
	private final int size = 10;
	
	public ShipGrid() {
		super();
		setLayout(new GridLayout(size, size)); // set layout for the grid
		setUp();
		ships = new ArrayList<Ship>();
	}

	private void setUp() {
		JPanel[][] cells = super.getCells();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j< 10; j++) {
				add(cells[i][j]);
			}
		}
	}
	
//	public ShipGrid(JPanel gridPanel) {
//		this.setLayout(new BorderLayout()); // Use BorderLayout to place the headers
//        initializeGrid();
//
//        // Add the headers
//        JPanel rowHeader = new JPanel(new GridLayout(size, 1));
//        JPanel columnHeader = new JPanel(new GridLayout(1, size));
//
//        for (int i = 0; i < size; i++) {
//            rowHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
//            columnHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
//        }
//
//        // Add the headers and the grid to the ShipGrid
//        this.add(rowHeader, BorderLayout.WEST);
//        this.add(columnHeader, BorderLayout.NORTH);
//        this.add(gridPanel, BorderLayout.CENTER);
//	}

	// public void placeShot(int type, int x, int y){
	// 	int[][] grid = getGrid();
	// 	grid[x][y] = type; //hit, miss
	// 	setGridArray(grid);
	// }
	
	public void initializeGrid() {
		JPanel[][] cells = super.getCells();
		int[][] grid = super.getGrid();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				switch (grid[i][j]) {
					case 1:
						//hit
						cells[i][j].setBackground(Color.RED);
						break;
					
					case 2:
						//miss
						cells[i][j].setBackground(Color.YELLOW);
						break;
					
					case 20:
						//gray
						cells[i][j].setBackground(Color.GRAY);
						break;
					
					case 21:
						//blue
						cells[i][j].setBackground(Color.BLUE);
						break;
						
					case 22:
						//green
						cells[i][j].setBackground(Color.GREEN);
						break;
						
					case 23:
						//orange
						cells[i][j].setBackground(Color.ORANGE);
						break;
						
					case 24:
						//magenta
						cells[i][j].setBackground(Color.MAGENTA);
						break;
						
					default:
						cells[i][j].setBackground(this.getBackground());
						break;
				}
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
	
	public void setGridArray(int[][] grid) {
		super.setGrid(grid);
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
	
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public int[][] getGridasArray(){
		return super.getGrid();
	}

	public Feedback update(int[] lastShot){
		grid = super.getGrid();
		int whatThere = grid[lastShot[0]][lastShot[1]];
		Feedback feedback = new Feedback("","");
		feedback.setDetailedMessage("");
		
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
	
//	@Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        // Add the numbers outside the grid here
//        for (int i = 0; i < size; i++) {
//            // Draw row numbers on the left side
//            g.drawString(Integer.toString(i), 0, i * (this.getHeight() / size) + (this.getHeight() / size / 2));
//            // Draw column numbers on the top
//            g.drawString(Integer.toString(i), i * (this.getWidth() / size) + (this.getWidth() / size / 2), 10);
//        }
//    }
}
