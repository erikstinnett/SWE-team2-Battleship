
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import Controller.*;
import Panel.*;

import javax.swing.*;
import Server.*;
import Utility.Feedback;

public class GameGUI extends JFrame{

	public GameGUI(String host) {
		
		this.setTitle("Battleship");
		this.setMinimumSize(new Dimension(960, 760));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    // Create the card layout container.
	    CardLayout cardLayout = new CardLayout();
	    JPanel container = new JPanel(cardLayout);
	    
	    //Create player client and and open connection
	    GameClient gameClient = new GameClient();
		// gameClient.setHost("localhost");
    	// gameClient.setPort(8300);

	    this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		        exitProcedure(gameClient);
		    }
		});

		gameClient.setHost(host);
    	gameClient.setPort(8300);

		try {
			gameClient.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //Create controllers 
	    CreateAccountControl cac = new CreateAccountControl(container, gameClient);
	    EndofGameControl egc = new EndofGameControl(container, gameClient);
	    InitialControl ic = new InitialControl(container);
	    LoginControl lc  = new LoginControl(container, gameClient);
	    MenuControl mc = new MenuControl(container, gameClient);
	    ScoreboardControl sc = new ScoreboardControl(container, gameClient);
	    StartofGameControl sgc = new StartofGameControl(container, gameClient);
	    GameControl gc = new GameControl(container, gameClient);

		//Set Client controllers
		gameClient.setLoginControl(lc);
		gameClient.setCreateAccountControl(cac);
		gameClient.setMenuControl(mc);
		gameClient.setScoreboardControl(sc);
		gameClient.setStartofGameControl(sgc);
		gameClient.setGameControl(gc);
		gameClient.setEndofGameControl(egc);
	   
	    //Create panels for each controller
	    // ex. JPanel view1 = new InitialPanel(ic)
	    JPanel view1 = new InitialPanel(ic);
	    JPanel view2 = new LoginPanel(lc);
	    JPanel view3 = new CreateAccountPanel(cac);
	    JPanel view4 = new MenuPanel(mc);
	    JPanel view5 = new StartOfGamePanel(sgc);
	    JPanel view6 = new GamePanel(gc);
	    JPanel view7 = new ScoreboardPanel(sc);
	    JPanel view8 = new EndGamePanel(egc);

	    // add each view to the container
	    
	    container.add(view1, "InitialPanel");
	    container.add(view2, "LoginPanel");
	    container.add(view3, "CreateAccountPanel");
	    container.add(view4, "MenuPanel");
	    container.add(view5, "StartofGamePanel");
	    container.add(view6, "GamePanel");
	    container.add(view7, "ScoreboardPanel");
	    container.add(view8, "EndGamePanel");

	    //show default container
	    cardLayout.show(container, "InitialPanel");
	    this.add(container, BorderLayout.CENTER);
	    this.setSize(550, 350);
	    this.setVisible(true);
	}

	public void exitProcedure(GameClient gameClient) {
		Feedback feedback = new Feedback("Player is closing the application", "CloseApp");
		try {
			gameClient.sendToServer(feedback);
		} catch (IOException e) {}
	    this.dispose();
	    System.exit(0);
	}
	
	public static void main(String[] args) {
		new GameGUI(args[0]);
	}
}
