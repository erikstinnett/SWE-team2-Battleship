package Controller;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Data.StartofGameData;
import Panel.StartOfGamePanel;
//import Panel.StartOfGamePanel.DraggableShip;
//import Panel.StartOfGamePanel.Grid;
import Server.GameClient;
import Utility.Ship;
import Utility.ShipGrid;

public class StartofGameControl extends MouseAdapter implements ActionListener {

	private JPanel container;
	private GameClient gameClient;
	private Point offset;
	
	public StartofGameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	// TO-DO figure out how turn order will be handled!
	public void startGame(Boolean goesFirst){
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "GamePanel");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();
		StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
		
		if (action.equals("Confirm Ship Placement")) {
			if (sogPanel.getShips().size() != 5) {
				return;
			}
			sogPanel.setButtonStatus(false);
			for (Ship i : sogPanel.getShips()) {
				i.removeMouseListener(this);
				i.removeMouseMotionListener(this);
			}
			sogPanel.setStatus("Waiting on Opponent :)");
			//ShipGrid shipGrid = new ShipGrid(sogPanel.getShips());
			//StartofGameData sogData = new StartofGameData(shipGrid);
//			
//			try {
//				gameClient.sendToServer(sogData);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
		else if (action.equals("Toggle Ship Orientation")) {
			Ship selectedShip = sogPanel.getSelectedShip();
			if (selectedShip != null) {
	    		selectedShip.toggleOrientation();
	    		selectedShip.setBounds(calculateNewBounds(selectedShip));
	    		mouseReleased(new MouseEvent(selectedShip, 0, 0, 0, (int)selectedShip.getLocation().getX(), (int)selectedShip.getLocation().getY(), 1, false));
	    		sogPanel.getLayeredPane().repaint();
	    	}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		offset = e.getPoint();
        Ship shipPanel = (Ship) e.getSource();
        StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
       
        
        //Clear the ships previous position before moving it
        if (shipPanel.getLastRow() != -1 && shipPanel.getLastCol() != -1) {
        	sogPanel.getGrid().clearShip(shipPanel);
        }
        
        sogPanel.setSelectedShip((Ship) e.getSource());
        sogPanel.setCurrentShip((Ship)e.getSource());
     // Bring the dragged ship to the top
        JLayeredPane layeredPane = sogPanel.getLayeredPane();
        layeredPane.setComponentZOrder(shipPanel, 0);
        layeredPane.revalidate();
        layeredPane.repaint();
	}
	
	@Override
    public void mouseDragged(MouseEvent e) {
		StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
		Ship selectedShip = sogPanel.getSelectedShip();
    	Point current = selectedShip.getLocation();
        int x = current.x - offset.x + e.getX();
        int y = current.y - offset.y + e.getY();
        selectedShip.setLocation(x, y);
        selectedShip.getParent().repaint();
    }
	
	@Override
    public void mouseReleased(MouseEvent e) {
    	Ship shipPanel = (Ship) e.getSource();
    	StartOfGamePanel sogPanel = (StartOfGamePanel)container.getComponent(4);
    	ShipGrid grid = sogPanel.getGrid();
    	
    	// Convert the location to grid coordinates
    	Point locationOnGrid = SwingUtilities.convertPoint(shipPanel,  new Point(0, 0),  grid);
    	int row = locationOnGrid.y / Ship.getCELL_SIZE();
    	int col = locationOnGrid.x / Ship.getCELL_SIZE();
    	
    	// Check if the location is within grid bounds
    	if (row< 0 || row >= grid.getGridSize() || col < 0 || col >= grid.getGridSize()) {
    		if (shipPanel.getLastRow() != -1 && shipPanel.getLastCol() != -1) {
    			grid.clearShip(shipPanel);
    			shipPanel.setLastPosition(-1,  -1); // Reset last position since it's off-grid now
    		}
    	} else {
    		grid.placeShip(shipPanel,  row, col);
    		shipPanel.setLastPosition(row,  col);; // Set the new last position
    	}
    	if (row >= 0 && row < grid.getGridSize() && col >= 0 && col < grid.getGridSize() && 
                (shipPanel.isVertical() ? row + shipPanel.getShipSize() <= grid.getGridSize() : col + shipPanel.getShipSize() <= grid.getGridSize())) {
                grid.placeShip(shipPanel, row, col);
            } else {
                // If the ship is outside the grid, reset its position and show an error message
                JOptionPane.showMessageDialog(grid, "Please place the ship inside the grid.", "Placement Error", JOptionPane.ERROR_MESSAGE);

            }
        System.out.println("Dropped at grid coordinates: Row = " + row + ", Col: " + col);
    }
	
	private Rectangle calculateNewBounds(Ship ship) {
		// This method should calculate the new bounds for the ship
        // based on its current position and orientation
        // For example:
        int x = ship.getBounds().x;
        int y = ship.getBounds().y;
        if (ship.isVertical()) {
            return new Rectangle(x, y, Ship.getCELL_SIZE(), ship.getShipSize() * Ship.getCELL_SIZE());
        } else {
            return new Rectangle(x, y, ship.getShipSize() * Ship.getCELL_SIZE(), Ship.getCELL_SIZE());
        }
	}

}
