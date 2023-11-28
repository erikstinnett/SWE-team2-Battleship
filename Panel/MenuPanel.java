package Panel;

import javax.swing.*;

import Controller.MenuControl;

import java.awt.*;

public class MenuPanel extends JPanel {
	
    public MenuPanel(MenuControl mc) {
    	//Create Main Menu Battleship TitleLabel
    	//Can be adjusted accordingly later
    	JLabel titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);
    	titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    	titleLabel.setForeground(Color.BLUE);
    	
    	//Create Buttons
    	JButton loginButton = new JButton("Login!");
    	//loginButton.addActionListener(mc);
    	JPanel loginButtonBuffer = new JPanel();
    	loginButtonBuffer.add(loginButton);
    	
    	// Create the exit button.
        JButton exitButton = new JButton("Exit");
        //exitButton.addActionListener(mc);
        JPanel exitButtonBuffer = new JPanel();
        exitButtonBuffer.add(exitButton);
        
        // Arrange the components in a grid.
        JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
        grid.add(titleLabel);
        grid.add(loginButtonBuffer);
        grid.add(exitButtonBuffer);
        
        this.add(grid);	
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
//
//    private static void createAndShowGUI() {
//        // Create the window.
//        JFrame frame = new JFrame("Menu Test");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Add the MenuPanel instance to the frame.
//        MenuControl mc = new MenuControl();
//        MenuPanel menuPanel = new MenuPanel(mc);
//        frame.getContentPane().add(menuPanel);
//
//        // Display the window.
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
    
}
