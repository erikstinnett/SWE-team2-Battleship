package Panel;

import javax.swing.*;
import java.awt.*;
import Controller.ScoreboardControl;

public class ScoreboardPanel extends JPanel {	
	private JTable scoreTable;
	
	
    public ScoreboardPanel(ScoreboardControl scoreboardControl) {
        // Initialize scoreboard
    	setLayout(new BorderLayout(10,10));
    	   	
    	JLabel leaderboardLabel = new JLabel("Battleship Leaderboard", JLabel.CENTER);
    	leaderboardLabel.setFont(new Font("Serif", Font.BOLD, 24));
    	
    	//Button to exit leaderboard
    	JButton exitLeaderboard = new JButton("Exit");
    	
    	
    	String[] columnNames = {"Username", "Score"};
    	//Just sample data, can change accordingly to the database
    	Object [][] data ={
    			{"Player 1", 100}, 
    			{"Player 2", 50}
    	};
    	
    	scoreTable = new JTable(data, columnNames);
    	scoreTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        scoreTable.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        
        add(leaderboardLabel, BorderLayout.NORTH); // Title at the top
        add(scrollPane, BorderLayout.CENTER); // Table in the center
        add(exitLeaderboard, BorderLayout.SOUTH); // Exit button at the bottom
        
        exitLeaderboard.addActionListener(e -> {
            // Placeholder for exit functionality
            System.out.println("Exit button clicked");
            
        });
    }

public static void main(String[] args) {
    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    });
}

private static void createAndShowGUI() {
    // Make sure we have nice window decorations.
    //JFrame.setDefaultLookAndFeelDecorated(true);

    // Create and set up the window.
    JFrame frame = new JFrame("Scoreboard Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create and set up the content pane.
    ScoreboardPanel newContentPane = new ScoreboardPanel(new ScoreboardControl());
    newContentPane.setOpaque(true); // content panes must be opaque
    frame.setContentPane(newContentPane);

    // Display the window.
    frame.pack();
    frame.setVisible(true);
}
}

