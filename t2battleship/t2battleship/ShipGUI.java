package t2battleship;

import java.awt.*;
import javax.swing.*;

public class ShipGUI extends JFrame {

	public ShipGUI() {
		this.setTitle("Ship GUI");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Create the card layout container.
	    CardLayout cardLayout = new CardLayout();
	    JPanel container = new JPanel(cardLayout);
	    // Instantiate client object here
	    
	    //Open connection (ex. client.openConnection();)
	    //Create controllers and have container as parameter
	    //Create views (Jpanel viewx) for each controller
	    //Add views to cardlayout container (container.add(viewx,"x");)
	    //Show initial view
	    
	    //Add card layout container to JFrame (this.add(container, LAYOUT);
	    
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ShipGUI();
	}
}
