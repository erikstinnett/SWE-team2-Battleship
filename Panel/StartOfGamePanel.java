package Panel;
import Controller.StartofGameControl;
import Utility.Ship;
import Utility.ShipGrid;

import java.awt.*;
//import java.awt.dnd.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class StartOfGamePanel extends JPanel {
    private ShipGrid grid;
    private JLabel playerStatus;
    private JButton confirmPlacement;
    private JLayeredPane layeredPane;
    private Ship currentShip;
    private JButton toggleOrientationButton;
    private Ship selectedShip;

    public StartOfGamePanel(StartofGameControl control) {
        grid = new ShipGrid();
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
        addShip("Carrier", Color.GRAY, initialX, initialY, control);     // Carrier
        initialY += shipSpacing;
        addShip("Battleship", Color.BLUE, initialX, initialY, control);     // Battleship
        initialY += shipSpacing;
        addShip("Cruiser", Color.GREEN, initialX, initialY, control);    // Cruiser
        initialY += shipSpacing;
        addShip("Submarine", Color.ORANGE, initialX, initialY, control);   // Submarine
        initialY += shipSpacing;
        addShip("Destroyer", Color.MAGENTA, initialX, initialY, control);      // Destroyer

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
    
    public void setStatus(String status) {
    	playerStatus.setText(status);
    }
    
    private void addShip(String name, Color color, int x, int y, StartofGameControl control) {
        Ship ship = new Ship(name, color, control);
//        ships.add(ship);
        ship.setBounds(x, y, ship.getPreferredSize().width, ship.getPreferredSize().height);
        layeredPane.add(ship, Integer.valueOf(1)); // Add to a higher layer than grid
    }
    
    public ArrayList<Ship> getShips(){
    	return grid.getShips();
    }
    
    public Ship getSelectedShip() {
    	return selectedShip;
    }
    
    public void setSelectedShip(Ship selectedShip) {
    	this.selectedShip = selectedShip;
    }
    
    public JLayeredPane getLayeredPane() {
    	return layeredPane;
    }
    
    public ShipGrid getGrid() {
    	return grid;
    }
    
    public Ship getCurrentShip() {
    	return currentShip;
    }
    
    public void setCurrentShip(Ship currentShip) {
    	this.currentShip = currentShip;
    }
 
    public void setButtonStatus(Boolean bool) {
    	confirmPlacement.setEnabled(bool);
    	toggleOrientationButton.setEnabled(bool);
    }
}