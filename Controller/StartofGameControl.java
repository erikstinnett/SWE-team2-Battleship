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
import Panel.GamePanel;
import Panel.StartOfGamePanel;
//import Panel.StartOfGamePanel.DraggableShip;
//import Panel.StartOfGamePanel.Grid;
import Server.GameClient;
import Utility.Feedback;
import Utility.Ship;
import Utility.ShipGrid;

public class StartofGameControl extends MouseAdapter implements ActionListener {

	private JPanel container;
	private GameClient gameClient;
	private Point offset;

	//Data
	StartofGameData sogData;
	//Panel
	StartOfGamePanel sogPanel;
	
	public StartofGameControl(JPanel container, GameClient gameClient) {
		this.container = container;
		this.gameClient = gameClient;
	}
	
	// TO-DO figure out how turn order will be handled!
	public void startGame(Boolean goesFirst, String msg){
		CardLayout cardLayout = (CardLayout) container.getLayout();
		sogPanel = (StartOfGamePanel)container.getComponent(4);
		GamePanel gp = (GamePanel) container.getComponent(5);
		gp.drawShip(sogPanel.getGrid());
		cardLayout.show(container, "GamePanel");
		//set initial turn order
		gp.setTurnOrder(goesFirst, msg); 
	}

	//sets the panel status
	public void setStatus(String status){
		sogPanel.setStatus(status);
	}

	//sends sogData
	public void sendSOGdata(String username){
		sogData.setPlayerUsername(username);
		try {
			gameClient.sendToServer(sogData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();
		sogPanel = (StartOfGamePanel)container.getComponent(4);
		
		if (action.equals("Confirm Ship Placement")) {
			if (sogPanel.getShips().size() != 5) {
				JOptionPane.showMessageDialog(sogPanel.getGrid(), "Please place all of the ships correctly >:(", "Submission Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			sogPanel.setButtonStatus(false);
			for (Ship i : sogPanel.getShips()) {
				i.removeMouseListener(this);
				i.removeMouseMotionListener(this);
			}
			// sogPanel.setStatus("Waiting on Opponent :)");
			sogData = new StartofGameData(sogPanel.getGrid());

			//set confirm button status
			sogPanel.setButtonStatus(false);

			//send the server that they are ready to play
			Feedback feedback = new Feedback("CreateGame", "CreateGame");
			try {
				gameClient.sendToServer(feedback);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		else if (action.equals("Toggle Ship Orientation")) {
			Ship selectedShip = sogPanel.getSelectedShip();
			if (selectedShip != null) {
	    		selectedShip.toggleOrientation();
	    		selectedShip.setBounds(calculateNewBounds(selectedShip));
	    		sogPanel.getGrid().clearShip(selectedShip);
	    		mouseReleased(new MouseEvent(selectedShip, 0, 0, 0, (int)selectedShip.getLocation().getX(), (int)selectedShip.getLocation().getY(), 1, false));
	    		sogPanel.getLayeredPane().repaint();
	    	}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		offset = e.getPoint();
        Ship shipPanel = (Ship) e.getSource();
        sogPanel = (StartOfGamePanel)container.getComponent(4);
       
        
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
		sogPanel = (StartOfGamePanel)container.getComponent(4);
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
    	sogPanel = (StartOfGamePanel)container.getComponent(4);
    	ShipGrid grid = sogPanel.getGrid();
    	
    	// Convert the location to grid coordinates
    	Point locationOnGrid = SwingUtilities.convertPoint(shipPanel,  new Point(0, 0),  grid);
    	int row = locationOnGrid.y / Ship.getCELL_SIZE();
    	int col = locationOnGrid.x / Ship.getCELL_SIZE();
    	boolean valid = true;
    	
    	// Check if the location is within grid bounds
    	if (row< 0 || row >= grid.getGridSize() || col < 0 || col >= grid.getGridSize()) {
    		if (shipPanel.getLastRow() != -1 && shipPanel.getLastCol() != -1) {
    			grid.clearShip(shipPanel);
    			shipPanel.setLastPosition(-1,  -1); // Reset last position since it's off-grid now
    		}
    	} else {
    		for (int i = 0; i < shipPanel.getShipSize(); i ++) {
            	if (shipPanel.isVertical()) {
            		if (grid.getGridasArray()[row + i][col] != 0) {
            			valid = false;
            			break;
            		}
            	}
            	else {
            		if (grid.getGridasArray()[row][col + i] != 0) {
            			valid = false;
            			break;
            		}
            	}
            }
			if(valid) {
				grid.placeShip(shipPanel, row, col);
				shipPanel.setLastPosition(row,  col); // Set the new last position
			}
			else {
				JOptionPane.showMessageDialog(grid, "Please make sure your ships are not overlapping.", "Placement Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
    		 
    	}
    	if (row >= 0 && row < grid.getGridSize() && col >= 0 && col < grid.getGridSize() && 
                (shipPanel.isVertical() ? row + shipPanel.getShipSize() <= grid.getGridSize() : col + shipPanel.getShipSize() <= grid.getGridSize())) {
    			if(valid) {
    				grid.placeShip(shipPanel, row, col);
    			}
    			else {
    				JOptionPane.showMessageDialog(grid, "Please make sure your ships are not overlapping.", "Placement Error", JOptionPane.ERROR_MESSAGE);
    			}
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
