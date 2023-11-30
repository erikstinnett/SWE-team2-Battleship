package Panel;
import Controller.StartofGameControl;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class StartOfGamePanel extends JPanel {
    private Grid grid;
    private JLabel playerStatus;
    private JButton confirmPlacement;
    private JPanel shipsPanel;
    private JLayeredPane layeredPane;
    private Object ships; // This could be a representation of ships for the UI
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
        addDraggableShip(5, Color.GRAY, initialX, initialY);     // Carrier
        initialY += shipSpacing;
        addDraggableShip(4, Color.BLUE, initialX, initialY);     // Battleship
        initialY += shipSpacing;
        addDraggableShip(3, Color.GREEN, initialX, initialY);    // Cruiser
        initialY += shipSpacing;
        addDraggableShip(3, Color.YELLOW, initialX, initialY);   // Submarine
        initialY += shipSpacing;
        addDraggableShip(2, Color.RED, initialX, initialY);      // Destroyer

        // Set up the button panel with FlowLayout for appropriate button size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        toggleOrientationButton = new JButton("Toggle Ship Orientation");
        toggleOrientationButton.addActionListener(e -> toggleShipOrientation());
        buttonPanel.add(toggleOrientationButton);

        // Set up the main layout of the StartOfGamePanel
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        add(playerStatus, BorderLayout.NORTH);
        add(confirmPlacement, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);

        // Set preferred sizes for the components
        confirmPlacement.setPreferredSize(new Dimension(200, 50));
        playerStatus.setPreferredSize(new Dimension(200, 50));
        toggleOrientationButton.setPreferredSize(new Dimension(200, 50));
        setPreferredSize(new Dimension(1000, 800));
    }
    
    private void addDraggableShip(int size, Color color, int x, int y) {
        DraggableShip ship = new DraggableShip(size, color);
        ship.setBounds(x, y, ship.getPreferredSize().width, ship.getPreferredSize().height);
        layeredPane.add(ship, Integer.valueOf(1)); // Add to a higher layer than grid
    }
    
    private void toggleShipOrientation() {
    	if (selectedShip != null) {
    		selectedShip.toggleOrientation();
    		selectedShip.setBounds(calculateNewBounds(selectedShip));
    		layeredPane.repaint();
    	}
    }
    
    private Rectangle calculateNewBounds(DraggableShip ship) {
        // This method should calculate the new bounds for the ship
        // based on its current position and orientation
        // For example:
        int x = ship.getBounds().x;
        int y = ship.getBounds().y;
        if (ship.isVertical()) {
            return new Rectangle(x, y, DraggableShip.CELL_SIZE, ship.getShipSize() * DraggableShip.CELL_SIZE);
        } else {
            return new Rectangle(x, y, ship.getShipSize() * DraggableShip.CELL_SIZE, DraggableShip.CELL_SIZE);
        }
    }
    
    public void setButtonStatus(Boolean bool) {
    	confirmPlacement.setEnabled(bool);
    }
    
    class DraggableShip extends JPanel {
        private int shipSize;
        private Ship ship;
        private boolean isVertical;
        private static final int CELL_SIZE = 40;

        public DraggableShip(int shipSize, Color color) {
            this.shipSize = shipSize;
            this.ship = new Ship(shipSize);
            this.isVertical = ship.isVertical;
            setPreferredSize(calculateDimension(shipSize, isVertical)); // Assuming each grid cell is 40x40
            setBackground(color);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            ship = new Ship(shipSize);

            MouseAdapter ma = new ShipMouseAdapter();
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }
        
        public boolean isVertical()
        {
        	return isVertical;
        }
        
        private Dimension calculateDimension(int shipSize, boolean isVertical) {
            return isVertical
                ? new Dimension(CELL_SIZE, shipSize * CELL_SIZE)
                : new Dimension(shipSize * CELL_SIZE, CELL_SIZE);
        }
        public void toggleOrientation() {
        	isVertical = !isVertical;
        	ship.toggleOrientation();
        	setPreferredSize(calculateDimension(ship.getSize(),isVertical));
        	revalidate();
        	repaint();
        }
        
        public Ship getShip()
        {
        	return ship;
        }

        private class ShipMouseAdapter extends MouseAdapter {
            Point offset;

            @Override
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
                DraggableShip shipPanel = (DraggableShip) e.getSource();
                selectedShip = (DraggableShip) e.getSource();
                currentShip = ((DraggableShip)e.getSource()).getShip();
             // Bring the dragged ship to the top
                shipsPanel.setComponentZOrder(shipPanel, 0);
                shipsPanel.revalidate();
                shipsPanel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            	Point current = getLocation();
                int x = current.x - offset.x + e.getX();
                int y = current.y - offset.y + e.getY();
                setLocation(x, y);
                getParent().repaint();
            }
            
            public void mouseReleased(MouseEvent e) {
            	DraggableShip shipPanel = (DraggableShip) e.getSource();
            }
        }

        // Getter for ship size
        public int getShipSize() {
            return shipSize;
        }
    }

    

    //new ship class
    class Ship
    {
    	private int size;
    	private boolean isVertical;
    	
    	public Ship(int size) 
    	{
    		this.size = size;
    		this.isVertical = true;
    	}
    	
    	public int getSize()
    	{
    		return size;
    	}
    	
    	public boolean isVertical()
    	{
    		return isVertical();
    	}
    	
    	public void toggleOrientation()
    	{
    		isVertical = !isVertical;
    	}
    }
    
    
    // Inner class for Grid, which is now a graphical component
    private class Grid extends JPanel implements DropTargetListener {
        private final int size = 10;
        private Cell[][] cells;

        public Grid() {
            setLayout(new GridLayout(size, size)); // Set layout for the grid
            cells = new Cell[size][size];
            new DropTarget(this,this);
            initializeGrid();
        }
        
        @Override
        public void drop(DropTargetDropEvent dtde) {
        	//Convert the drop point to grid coordinates
        	Point dropPoint = dtde.getLocation();
        	int row = dropPoint.y / (getHeight() / size);
        	int col = dropPoint.x / (getWidth() / size);
        	
        	placeShip(row,col);
        }
        
        private void placeShip(int row, int col) {
        	if(currentShip == null) return;
        	
        	int shipSize = currentShip.getSize();
        	boolean isVertical = currentShip.isVertical();
        	
        	for(int i = 0; i < shipSize; i++)
        	{
        		int currentRow = isVertical ? row + i : row;
        		int currentCol = isVertical ? col : col + i;
        		
        		if(currentRow < size && currentCol < size) {
        			cells[currentRow][currentCol].setShipPart(currentShip);
        		}
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
            
            public void setShipPart(Ship ship) {
            	this.shipPart = ship;
            }
            
            public boolean hasShip() {
            	return shipPart != null;
            }
        }

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			// TODO Auto-generated method stub
			
		}
        
        
    }
}




