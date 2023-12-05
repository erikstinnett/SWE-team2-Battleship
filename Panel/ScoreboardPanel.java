package Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import Controller.ScoreboardControl;

public class ScoreboardPanel extends JPanel {	
	private JLabel[][] labels;
	
    public ScoreboardPanel(ScoreboardControl scoreboardControl) {
        // Initialize scoreboard
    	setLayout(new BorderLayout(10,10));
    	labels = new JLabel[7][3];
    	
    	JLabel leaderboardLabel = new JLabel("Battleship Leaderboard", JLabel.CENTER);
    	leaderboardLabel.setFont(new Font("Serif", Font.BOLD, 24));
    	
    	//Button to exit leaderboard
    	JButton exitLeaderboard = new JButton("Exit");
    	
    	JPanel center = new JPanel(new GridLayout(7,3));
    	
    	for (int i = 0; i < 7; i++) {
    		for (int j = 0; j < 3; j++) {
    			labels[i][j] = new JLabel("", JLabel.CENTER);
    			labels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
    			if (i == 0) {
    				if (j== 0) {
    	    			labels[i][j].setText("Username");
    	    		}
    	    		else if (j == 1) {
    	    			labels[i][j].setText("Wins");
    	    		}
    	    		else if (j == 2) {
    	    			labels[i][j].setText("Losses");
    	    		}
    			}
    			center.add(labels[i][j]);
    		}
    	}
        
        add(leaderboardLabel, BorderLayout.NORTH); // Title at the top
        add(center, BorderLayout.CENTER); // Table in the center
        add(exitLeaderboard, BorderLayout.SOUTH); // Exit button at the bottom
        
        exitLeaderboard.addActionListener(scoreboardControl);
    }
    
    public void setTable(String[][] data) {
    	
    	for (int i = 0; i < data.length; i++) {
    		for (int j = 0; j < 3; j++) {
    			labels[i + 1][j].setText(data[i][j]);
    		}
    	}
    }
}