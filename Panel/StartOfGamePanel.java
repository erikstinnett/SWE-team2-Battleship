package Panel;
import Controller.StartofGameControl;
import Utility.Ship;

import java.awt.*;
//import java.awt.dnd.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class StartOfGamePanel extends JPanel {
    private Grid grid;
    private JLabel playerStatus;
    private JButton confirmPlacement;
//    private JPanel shipsPanel;
    private JLayeredPane layeredPane;
    private ArrayList<Ship> ships; // This could be a representation of ships for the UI
    private Ship currentShip;
    private JButton toggleOrientationButton;
    private DraggableShip selectedShip;

    public StartOfGamePanel(StartofGameControl control) {
        grid = new Grid();
        playerStatus = new JLabel("Place Your Ships!");
        playerStatus.setHorizontalAlignment(JLabel.CENTER);
        confirmPlacement = new JButton("Confirm Ship Placement");

        // Initialize JLayeredPane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 800));
        
        // Configure and add the grid to the layeredPane
        int gridWidth = 600; // Keep the grid size or adjust as needed
        int gridHeight = 600;
        grid.setBounds(0, 0, gridWidth, gridHeight);
        layeredPane.add(grid, Integer.valueOf(0)); // Add grid to default layer (0)

        // Create and position ships
        int initialX = gridWidth + 10; // Place ships right next to the grid
        int initialY = 10; // Start with some padding from the top
        int shipSpacing = 50; // Adjust the space between ships

        // Add ships to the shipsPanel with new positions
        addDraggableShip("Carrier", Color.GRAY, initialX, initialY, control);     // Carrier
        initialY += shipSpacing;
        addDraggableShip("Battleship", Color.BLUE, initialX, initialY, control);     // Battleship
        initialY += shipSpacing;
        addDraggableShip("Cruiser", Color.GREEN, initialX, initialY, control);    // Cruiser
        initialY += shipSpacing;
        addDraggableShip("Submarine", Color.YELLOW, initialX, initialY, control);   // Submarine
        initialY += shipSpacing;
        addDraggableShip("Destroyer", Color.RED, initialX, initialY, control);      // Destroyer

        // Set up the button panel with FlowLayout for appropriate button size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        toggleOrientationButton = new JButton("Toggle Ship Orientation");
        toggleOrientationButton.addActionListener(control);
        buttonPanel.add(toggleOrientationButton);

        // Set up the main layout of the StartOfGamePanel
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        add(playerStatus, BorderLayout.NORTH);
        add(confirmPlacement, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);
        
        confirmPlacement.addActionListener(control);

        // Set preferred sizes for the components
        confirmPlacement.setPreferredSize(new Dimension(200, 50));
        playerStatus.setPreferredSize(new Dimension(200, 50));
        toggleOrientationButton.setPreferredSize(new Dimension(200, 50));
        setPreferredSize(new Dimension(1000, 800));
    }
    
    private void addDraggableShip(String name, Color color, int x, int y, StartofGameControl control) {
        DraggableShip ship = new DraggableShip(name, color, control);
        ship.setBounds(x, y, ship.getPreferredSize().width, ship.getPreferredSize().height);
        layeredPane.add(ship, Integer.valueOf(1)); // Add to a higher layer than grid
    }
    
    public ArrayList<Ship> getShips(){
    	return ships;
    }
    
//    private void toggleShipOrientation() {
//    	if (selectedShip != null) {
//    		selectedShip.toggleOrientation();
//    		selectedShip.setBounds(calculateNewBounds(selectedShip));
//    		layeredPane.repaint();
//    	}
//    }
    
    public DraggableShip getSelectedShip() {
    	return selectedShip;
    }
    
    public void setSelectedShip(DraggableShip selectedShip) {
    	this.selectedShip = selectedShip;
    }
    
    public JLayeredPane getLayeredPane() {
    	return layeredPane;
    }
    
    public Grid getGrid() {
    	return grid;
    }
    
    public Ship getCurrentShip() {
    	return currentShip;
    }
    
    public void setCurrentShip(Ship currentShip) {
    	this.currentShip = currentShip;
    }
    
//    private Rectangle calculateNewBounds(DraggableShip ship) {
//        // This method should calculate the new bounds for the ship
//        // based on its current position and orientation
//        // For example:
//        int x = ship.getBounds().x;
//        int y = ship.getBounds().y;
//        if (ship.isVertical()) {
//            return new Rectangle(x, y, DraggableShip.CELL_SIZE, ship.getShipSize() * DraggableShip.CELL_SIZE);
//        } else {
//            return new Rectangle(x, y, ship.getShipSize() * DraggableShip.CELL_SIZE, DraggableShip.CELL_SIZE);
//        }
//    }
//    
    public void setButtonStatus(Boolean bool) {
    	confirmPlacement.setEnabled(bool);
    	toggleOrientationButton.setEnabled(bool);
    }
    
    public class DraggableShip extends JPanel {
        private Ship ship;
        private static final int CELL_SIZE = 40;
        
        private int lastRow = -1;
        private int lastCol = -1;

        public DraggableShip(String name, Color color, StartofGameControl control) {
        	ship = new Ship(name);
        	ships.add(ship);
            setPreferredSize(calculateDimension(ship.getSize(), ship.getOrientation())); // Assuming each grid cell is 40x40
            setBackground(color);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // MouseAdapter ma = new ShipMouseAdapter();
            addMouseListener(control);
            addMouseMotionListener(control);
        }
        
        public static int getCELL_SIZE() {
        	return CELL_SIZE;
        }
        
        public boolean isVertical()
        {
        	return ship.getOrientation();
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
        public void toggleOrientation() {
        	ship.toggleOrientation();
        	setPreferredSize(calculateDimension(ship.getSize(),ship.getOrientation()));
        	revalidate();
        	repaint();
        }
        
        public void setLastPosition(int row, int col) {
        	this.lastRow = row;
        	this.lastCol = col;
        }
        
        public Ship getShip()
        {
        	return ship;
        }

//        private class ShipMouseAdapter extends MouseAdapter {
//            Point offset;
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                offset = e.getPoint();
//                DraggableShip shipPanel = (DraggableShip) e.getSource();
//               
//                
//                //Claer the ships previous position before moving it
//                if (shipPanel.lastRow != -1 && shipPanel.lastCol != -1) {
//                	grid.clearShip(shipPanel);
//                }
//                
//                selectedShip = (DraggableShip) e.getSource();
//                currentShip = ((DraggableShip)e.getSource()).getShip();
//             // Bring the dragged ship to the top
//                layeredPane.setComponentZOrder(shipPanel, 0);
//                layeredPane.revalidate();
//                layeredPane.repaint();
//            }
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//            	Point current = getLocation();
//                int x = current.x - offset.x + e.getX();
//                int y = current.y - offset.y + e.getY();
//                setLocation(x, y);
//                getParent().repaint();
//            }
//            
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            	DraggableShip shipPanel = (DraggableShip) e.getSource();
//            	
//            	// Convert the location to grid coordinates
//            	Point locationOnGrid = SwingUtilities.convertPoint(shipPanel,  new Point(0, 0),  grid);
//            	int row = locationOnGrid.y / DraggableShip.CELL_SIZE;
//            	int col = locationOnGrid.x / DraggableShip.CELL_SIZE;
//            	
//            	// Check if the location is within grid bounds
//            	if (row< 0 || row >= grid.size || col < 0 || col >= grid.size) {
//            		if (shipPanel.lastRow != -1 && shipPanel.lastCol != -1) {
//            			grid.clearShip(shipPanel);
//            			shipPanel.setLastPosition(-1,  -1); // Reset last position since it's off-grid now
//            		}
//            	} else {
//            		grid.placeShip(shipPanel,  row, col);
//            		shipPanel.setLastPosition(row,  col);; // Set the new last position
//            	}
//            	if (row >= 0 && row < grid.size && col >= 0 && col < grid.size && 
//                        (shipPanel.isVertical() ? row + shipPanel.getShipSize() <= grid.size : col + shipPanel.getShipSize() <= grid.size)) {
//                        grid.placeShip(shipPanel, row, col);
//                    } else {
//                        // If the ship is outside the grid, reset its position and show an error message
//                        JOptionPane.showMessageDialog(grid, "Please place the ship inside the grid.", "Placement Error", JOptionPane.ERROR_MESSAGE);
//
//                    }
//                System.out.println("Dropped at grid coordinates: Row = " + row + ", Col: " + col);
//            }
//        }

        // Getter for ship size
        public int getShipSize() {
            return ship.getSize();
        }
    }

    

    //new ship class
//    class Ship
//    {
//    	private int size;
//    	private boolean isVertical;
//    	
//    	public Ship(int size) 
//    	{
//    		this.size = size;
//    		this.isVertical = true;
//    	}
//    	
//    	public int getSize()
//    	{
//    		return size;
//    	}
//    	
//    	public boolean isVertical()
//    	{
//    		return isVertical();
//    	}
//    	
//    	public void toggleOrientation()
//    	{
//    		isVertical = !isVertical;
//    	}
//    }
    
    
    // Inner class for Grid, which is now a graphical component
    public class Grid extends JPanel {//implements DropTargetListener {
        private final int size = 10;
        private Cell[][] cells;
        private Ship[][] shipPositions = new Ship[size][size];

        public Grid() {
            setLayout(new GridLayout(size, size)); // Set layout for the grid
            cells = new Cell[size][size];
            //new DropTarget(this,this);
            initializeGrid();
        }
        
//        @Override
//        public void drop(DropTargetDropEvent dtde) {
//        	//Convert the drop point to grid coordinates
//        	Point dropPoint = dtde.getLocation();
//        	int row = dropPoint.y / (getHeight() / size);
//        	int col = dropPoint.x / (getWidth() / size);
//        	
//        	placeShip(row,col);
//        }
        
        public void placeShip(DraggableShip ship, int rowStart, int colStart) {
            
            int shipSize = ship.getShipSize();
            boolean isVertical = ship.isVertical();
            
            // Check if the entire ship can be placed without going out of bounds
            if ((isVertical && (rowStart + shipSize) > size) || (!isVertical && (colStart + shipSize) > size)) {
                System.out.println("Ship placement is out of bounds.");
                return; // Ship placement is out of bounds
            }

            for (int i = 0; i < shipSize; i++) {
                int currentRow = isVertical ? rowStart + i : rowStart;
                int currentCol = isVertical ? colStart : colStart + i;
                
                shipPositions[currentRow][currentCol] = ship.getShip();
                cells[currentRow][currentCol].setShipPart(ship.getShip()); // Update the cell visually if needed
            }
        }
        
        public int getGridSize() {
        	return size;
        }
        
        public void clearShip(DraggableShip ship) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (shipPositions[row][col] == ship.getShip()) {
                        shipPositions[row][col] = null;
                        cells[row][col].clearShipPart(); // Assuming you have a method to clear the visual part of the cell
                    }
                }
            }
        }
        
        //for debugging can comment out.
        public void printShipPlacements() {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (shipPositions[row][col] != null) {
                        System.out.print("S "); // 'S' for ship
                    } else {
                        System.out.print(". "); // '.' for empty
                    }
                }
                System.out.println();
            }
        }

        private void initializeGrid() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = new Cell();
                    add(cells[i][j]); // Add cell to the grid panel
                }
            }
        }

        // Inner class for Cell, which is now a graphical component
        private class Cell extends JPanel {
            // Variables and methods to visually represent the cell
        	private Ship shipPart;
            public Cell() {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // Set other properties, like size, background color, etc.
            }
            
            public void clearShipPart() {
                repaint(); // Repaint the cell to update the UI
            }

			public void setShipPart(Ship ship) {
            	this.shipPart = ship;
            }
            
            public boolean hasShip() {
            	return shipPart != null;
            }
        }

//		@Override
//		public void dragEnter(DropTargetDragEvent dtde) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void dragOver(DropTargetDragEvent dtde) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void dropActionChanged(DropTargetDragEvent dtde) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void dragExit(DropTargetEvent dte) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void drop(DropTargetDropEvent dtde) {
//			// TODO Auto-generated method stub
//			
//		}
        
    }
}




