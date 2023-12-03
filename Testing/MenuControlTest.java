package Testing;

import static org.junit.Assert.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;
import Controller.MenuControl;
import Server.GameClient;

public class MenuControlTest {

    private MenuControl mp;
    private JPanel container;
    private GameClient gameClient;
    
    @Before
    public void setUp() {
        container = new JPanel();
        mp = new MenuControl(container, gameClient);
        container.setLayout(new CardLayout());
    }
    
    @Test
    public void testViewScoreboard() {        
        ActionEvent sentToServer = new ActionEvent(new JButton("Play!"), ActionEvent.ACTION_PERFORMED, "View Scoreboard!");
        try{
        	mp.actionPerformed(sentToServer);
        }catch (Exception err) {
        	fail("Exception caught");
        }
    }
    @Test
    public void testExitButton() {
        // Simulate "Exit" button click
        ActionEvent exitEvent = new ActionEvent(new JButton("Exit"), ActionEvent.ACTION_PERFORMED, "Exit");
        mp.actionPerformed(exitEvent);
        // Assert that the container is set to "InitialPanel"
        assertEquals("InitialPanel", container.getName());
    }
    @Test
    public void testPlayButton() {
        ActionEvent startGameEvent = new ActionEvent(new JButton("Play!"), ActionEvent.ACTION_PERFORMED, "Play!");
        mp.actionPerformed(startGameEvent);
        assertEquals("StartofGamePanel", container.getName());
    }
}
