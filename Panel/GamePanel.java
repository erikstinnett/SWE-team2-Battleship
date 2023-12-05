package Panel;

import javax.swing.*;

import Controller.GameControl;
import Data.GameData;
import Utility.ShipGrid;
import Utility.ShootGrid;

import java.awt.*;

public class GamePanel extends JPanel {

    private Utility.ShipGrid shipGrid; // Placeholder for your ship grid object
    private ShootGrid shootGrid;
    private JLabel playerStatus;
    private JButton fireButton;
    private JTextField shipGuess;
    private JPanel gridPanel;

    //turn order label
    JLabel turn_order = new JLabel("");
    
    //game data
    GameData gameData;

    public void setTurnOrder(Boolean bool, String msg){

        turn_order.setText(msg);
        fireButton.setEnabled(bool);
        
    }

    

    public GamePanel(GameControl gc) {
        setLayout(new BorderLayout(10, 10)); // Added gap between components
        
//        gridPanel = new JPanel(new GridLayout(10, 10));
        
        shipGrid = new ShipGrid();
        shipGrid.setPreferredSize(new Dimension(350, 350)); // Set preferred size for a square grid
        JPanel buffer = new JPanel(new BorderLayout());
        JPanel rowHeader = new JPanel(new GridLayout(10, 1));
        JPanel columnHeader = new JPanel(new GridLayout(1, 10));

        for (int i = 0; i < 10; i++) {
            rowHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
            columnHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
        }
        
        buffer.add(rowHeader, BorderLayout.WEST);
        buffer.add(columnHeader, BorderLayout.NORTH);
        buffer.add(shipGrid, BorderLayout.CENTER);

        shootGrid = new ShootGrid();
        shootGrid.setPreferredSize(new Dimension(350, 350)); // Set preferred size for a square grid
        JPanel buffer1 = new JPanel(new BorderLayout());
        JPanel rowHeader1 = new JPanel(new GridLayout(10, 1));
        JPanel columnHeader1 = new JPanel(new GridLayout(1, 10));

        for (int i = 0; i < 10; i++) {
            rowHeader1.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
            columnHeader1.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
        }
        
        buffer1.add(rowHeader1, BorderLayout.WEST);
        buffer1.add(columnHeader1, BorderLayout.NORTH);
        buffer1.add(shootGrid, BorderLayout.CENTER);

        playerStatus = new JLabel("Guess Your Opponents Ship Location!");
        // playerStatus.setHorizontalAlignment(JLabel.CENTER);

        fireButton = new JButton("Fire!");
        fireButton.addActionListener(gc); 

        shipGuess = new JTextField();

        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panela = new JPanel();
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel2a = new JPanel();

        // addCoordinateLabels();

        panel1.add(buffer1);
        panela.add(shipGuess);
        panela.add(fireButton);
        panela.add(turn_order);
        panel2.add(buffer);
        panel2a.add(playerStatus);

        JPanel gridPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        JPanel gridPanel2 = new JPanel(new GridLayout(2, 1, 0, 10));

        gridPanel.add(panel1);
        gridPanel.add(panela);
        gridPanel2.add(panel2);
        gridPanel2.add(panel2a);

        shipGuess.setPreferredSize(new Dimension(100, 30)); // Adjust the width and height as needed

        JPanel container = new JPanel();
        container.add(gridPanel);
        container.add(gridPanel2);

        this.add(container);
        // this.setSize(700,700);

        // gc.setGamePanel(this);
    }
    
    public void setButtonStatus(boolean bool) {
    	fireButton.setEnabled(bool);
    }
    
    public void drawShoot(ShootGrid shootGrid) {
    	this.shootGrid.setGridArray(shootGrid.getGrid());
    	this.shootGrid.initializeGrid();
    }
    
    public void drawShip(ShipGrid shipGrid) {
    	this.shipGrid.setGridArray(shipGrid.getGridasArray());
    	this.shipGrid.setShips(shipGrid.getShips());
    	this.shipGrid.initializeGrid();
    }
    
    public JPanel getGridPanel() {
    	return gridPanel;
    }

    //setter/getter for shipGuess
    public void setShipGuess(JTextField shipGuess){
        this.shipGuess = shipGuess;
    }

    public JTextField getShipGuess(){
        return this.shipGuess;
    }

    public ShipGrid getShipGrid(){
        return this.shipGrid;
    }

    public ShootGrid getShootGrid(){
        return this.shootGrid;
    }
}
