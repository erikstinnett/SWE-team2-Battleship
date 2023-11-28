
import java.awt.*;

import javax.swing.*;

public class GameGUI extends JFrame{

	public GameGUI() {
		
		this.setTitle("Battleship");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    // Create the card layout container.
	    CardLayout cardLayout = new CardLayout();
	    JPanel container = new JPanel(cardLayout);
	    
	    //Create player client and and open connection
	    
	    //Create controllers 
	    //Create panels for each controller
	    // ex. JPanel view1 = new InitialControl(ic)
	    
	    // add each view to the container
	    //show default container
	    
	    this.add(container, BorderLayout.CENTER);
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GameGUI();
	}
}
