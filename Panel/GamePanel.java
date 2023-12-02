package Panel;

import javax.swing.*;

import Controller.GameControl;
import Utility.ShipGrid;
import Utility.ShootGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private Utility.ShipGrid shipGrid; // Placeholder for your ship grid object
    private ShootGrid shootGrid;
    private JLabel playerStatus;
    private JButton fireButton;
    private JTextField shipGuess;
    private JPanel gridPanel;

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
        // fireButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         String guess = shipGuess.getText();
        //         try {
        //             String[] parts = guess.split(",");
        //             int x = Integer.parseInt(parts[0].trim());
        //             int y = Integer.parseInt(parts[1].trim());

        //             if (x >= 0 && x < 10 && y >= 0 && y < 10) {
        //                 shootAtGrid(x, y);
        //             } else {
        //                 JOptionPane.showMessageDialog(GamePanel.this,
        //                         "Invalid coordinates, please enter values between 0 and 9");
        //             }
        //         } catch (Exception ex) {
        //             JOptionPane.showMessageDialog(GamePanel.this,
        //                     "Invalid input format. Please enter coordinates in the format 'x,y'");
        //         }
        //     }
        // });

        shipGuess = new JTextField();

        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panela = new JPanel();
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel2a = new JPanel();

        // addCoordinateLabels();

        panel1.add(buffer1);
        panela.add(shipGuess);
        panela.add(fireButton);
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

    }
    
    public void setButtonStatus(boolean bool) {
    	fireButton.setEnabled(bool);
    }
    
    public void drawShoot(ShootGrid shootGrid) {
    	this.shootGrid.setGridArray(shootGrid.getGrid());
    	this.removeAll();
    	this.shootGrid.initializeGrid();
    }
    
    public void drawShip(ShipGrid shipGrid) {
    	this.shipGrid.setGridArray(shipGrid.getGridasArray());
    	this.shipGrid.setShips(shipGrid.getShips());
    	this.shipGrid.removeAll();
    	this.shipGrid.initializeGrid();
    }


//    private void shootAtGrid(int x, int y) {
//        boolean isHit = checkIfHit(x, y);
//
//        ShipGrid.ShipCell targetCell = shootGrid.cells[x][y];
//
//        // Color the cell based on hit or miss
//        if (isHit) {
//            targetCell.setBackground(Color.GREEN);
//        } else {
//            targetCell.setBackground(Color.RED);
//        }
//
//    }

//    private boolean checkIfHit(int x, int y) {
//        return false;
//    }
    
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

    //setter/getter for the shipGrid/shootGrid
//    public void setShipGrid(ShipGrid shipGrid){
//        this.shipGrid = shipGrid;
//    }

    public ShipGrid getShipGrid(){
        return this.shipGrid;
    }

//    public void setShootGrid(ShootGrid shootGrid){
//        this.shootGrid = shootGrid;
//    }

    public ShootGrid getShootGrid(){
        return this.shootGrid;
    }
    
    // Inner class for Grid
//    private class ShipGrid extends JPanel {
//        private final int size = 10;
//        private ShipCell[][] cells;
//        private boolean isPlayerGrid; // To distinguish between player's grid and target grid
//        private JPanel gridPanel;
//        private JPanel labelsColumn; // for the column headers
//        private JPanel labelsRow; // for the row headers

//        public ShipGrid(boolean isPlayerGrid) {
//            this.isPlayerGrid = isPlayerGrid;
//            this.setLayout(new BorderLayout()); // Use BorderLayout to place the headers
//
//            gridPanel = new JPanel(new GridLayout(size, size)); // Grid for the cells
//            cells = new ShipCell[size][size];
//            initializeGrid();
//
//            // Add the headers
//            JPanel rowHeader = new JPanel(new GridLayout(size, 1));
//            JPanel columnHeader = new JPanel(new GridLayout(1, size));
//
//            for (int i = 0; i < size; i++) {
//                rowHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
//                columnHeader.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
//            }
//
//            // Add the headers and the grid to the ShipGrid
//            this.add(rowHeader, BorderLayout.WEST);
//            this.add(columnHeader, BorderLayout.NORTH);
//            this.add(gridPanel, BorderLayout.CENTER);
//
//        }

//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            // Add the numbers outside the grid here
//            for (int i = 0; i < size; i++) {
//                // Draw row numbers on the left side
//                g.drawString(Integer.toString(i), 0, i * (this.getHeight() / size) + (this.getHeight() / size / 2));
//                // Draw column numbers on the top
//                g.drawString(Integer.toString(i), i * (this.getWidth() / size) + (this.getWidth() / size / 2), 10);
//            }
//        }

//        private void initializeGrid() {
//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    cells[j][i] = new ShipCell();
//                    gridPanel.add(cells[j][i]);
//                }
//            }
//        }

//        private class ShipCell extends JPanel {
//            public ShipCell() {
//                setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            }
//        }
//    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // public void run() {
    // createAndShowGUI();
    // }
    // });
    // }
    //
    // private static void createAndShowGUI() {
    // // Create the main window (frame)
    // JFrame frame = new JFrame("Battleship Game Test");
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //
    // // Create an instance of StartOfGamePanel
    // GamePanel GamePanel = new GamePanel(new GameControl()); // Assuming you have
    // a StartOfGameControl class
    // frame.add(GamePanel);
    //
    // // Configure the frame
    // frame.setPreferredSize(new Dimension(900, 600)); // Adjust as necessary
    // frame.setMinimumSize(frame.getPreferredSize()); // Prevent the frame from
    // being too small
    // frame.pack();
    // frame.setLocationRelativeTo(null);
    // frame.setVisible(true);
    // }
    // // Additional game-related methods
}
