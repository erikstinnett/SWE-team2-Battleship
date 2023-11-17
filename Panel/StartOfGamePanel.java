package Panel;

import java.awt.*;
import javax.swing.*;

public class StartOfGamePanel extends JPanel {
    private Grid grid;
    private JLabel playerStatus;
    private JButton confirmPlacement;
    private JPanel shipsPanel;
    private Object ships; // This could be a representation of ships for the UI

    public StartOfGamePanel(StartOfGameControl control) {
        grid = new Grid();
        
        playerStatus = new JLabel("Place Your Ship Placement!");
        playerStatus.setHorizontalAlignment(JLabel.CENTER);
        confirmPlacement = new JButton("Confirm Ship Placement");
        shipsPanel = new JPanel();
        
        
        setLayout(new BorderLayout());
        add(grid, BorderLayout.CENTER); // Add grid to the panel
        add(playerStatus, BorderLayout.NORTH);
        add(confirmPlacement, BorderLayout.SOUTH);
        add(shipsPanel, BorderLayout.EAST);
        
        confirmPlacement.setPreferredSize(new Dimension(200,100));
        playerStatus.setPreferredSize(new Dimension(200,100));
        shipsPanel.setPreferredSize(new Dimension(200,500));
        setPreferredSize(new Dimension(600,600));
    }

    public Object getShips() {
        return ships;
    }

    // Inner class for Grid, which is now a graphical component
    private class Grid extends JPanel {
        private final int size = 10;
        private Cell[][] cells;

        public Grid() {
            setLayout(new GridLayout(size, size)); // Set layout for the grid
            cells = new Cell[size][size];
            initializeGrid();
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
            public Cell() {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
                // Set other properties, like size, background color, etc.
            }
        }
    }

    
}



