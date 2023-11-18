package Panel;

import javax.swing.*;

import Controller.GameControl;

//import Controller.GameControl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GamePanel extends JPanel {
	
    private ShipGrid shipGrid; // Placeholder for your ship grid object
    private ShipGrid shootGrid;
    private JLabel playerStatus;
    private JButton fireButton;
    private JTextField shipGuess;
    
    
    public GamePanel(GameControl gc) {
        setLayout(new BorderLayout(10, 10)); // Added gap between components
        
        shipGrid = new ShipGrid(true);
        shipGrid.setPreferredSize(new Dimension(350, 350)); // Set preferred size for a square grid
        
        shootGrid = new ShipGrid(false);
        shootGrid.setPreferredSize(new Dimension(350, 350)); // Set preferred size for a square grid
        
        playerStatus = new JLabel("Guess Your Opponents Ship Location!");
        // playerStatus.setHorizontalAlignment(JLabel.CENTER);
        
        fireButton = new JButton("Fire!");
        
        shipGuess = new JTextField();

        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panela = new JPanel();
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel2a = new JPanel();

        panel1.add(shootGrid);
        panela.add(shipGuess);
        panela.add(fireButton);
        panel2.add(shipGrid);
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

    }
    
 // Inner class for Grid
    private class ShipGrid extends JPanel {
        private final int size = 10;
        private ShipCell[][] cells;
        private boolean isPlayerGrid; // To distinguish between player's grid and target grid

        public ShipGrid(boolean isPlayerGrid) {
            this.isPlayerGrid = isPlayerGrid;
            setLayout(new GridLayout(size, size));
            cells = new ShipCell[size][size];
            initializeGrid();
        }

        private void initializeGrid() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = new ShipCell();
                    add(cells[i][j]);
                }
            }
        }
        
        private class ShipCell extends JPanel {
            public ShipCell() {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }

    public void updateShipGrid(ShipGrid grid) {
        // Update logic for the ship grid
    }    
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Create the main window (frame)
        JFrame frame = new JFrame("Battleship Game Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of StartOfGamePanel
        GamePanel GamePanel = new GamePanel(new GameControl()); // Assuming you have a StartOfGameControl class
        frame.add(GamePanel);

        // Configure the frame
        frame.setPreferredSize(new Dimension(900, 600)); // Adjust as necessary
        frame.setMinimumSize(frame.getPreferredSize()); // Prevent the frame from being too small
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    // Additional game-related methods
}
