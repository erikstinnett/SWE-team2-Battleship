package Panel;

import javax.swing.*;
import java.awt.*;
import Controller.ScoreboardControl;

public class ScoreboardPanel extends JPanel {	
	private JTable scoreTable;
	
	
    public ScoreboardPanel() {
        // Initialize scoreboard
    	setLayout(new BorderLayout(10,10));
    }
    
    public void setTable(Object [][] data, ScoreboardControl scoreboardControl) {
    	JLabel leaderboardLabel = new JLabel("Battleship Leaderboard", JLabel.CENTER);
    	leaderboardLabel.setFont(new Font("Serif", Font.BOLD, 24));
    	
    	//Button to exit leaderboard
    	JButton exitLeaderboard = new JButton("Exit");
    	
    	
    	String[] columnNames = {"Username", "Wins", "Losses"};
    	
    	scoreTable = new JTable(data, columnNames);
    	scoreTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        scoreTable.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        
        add(leaderboardLabel, BorderLayout.NORTH); // Title at the top
        add(scrollPane, BorderLayout.CENTER); // Table in the center
        add(exitLeaderboard, BorderLayout.SOUTH); // Exit button at the bottom
        
        exitLeaderboard.addActionListener(scoreboardControl);
    }
}

