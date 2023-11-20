package Panel;

import javax.swing.;
import java.awt.;

public class MenuPanel extends JPanel {

    public MenuPanel(MenuControl mc) {
        //Create Main Menu Battleship TitleLabel
        //Can be adjusted accordingly later
        JLabel titleLabel = new JLabel("BATTLESHIP", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);

        //Create Buttons
        JButton playButton = new JButton("Play!");
        //loginButton.addActionListener(mc);
        JPanel loginButtonBuffer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButtonBuffer.add(playButton);

        // Create the exit button.
        JButton exitButton = new JButton("Exit");
        //exitButton.addActionListener(mc);
        JPanel exitButtonBuffer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitButtonBuffer.add(exitButton);

        //Create the view scoreboard button.

        JButton viewScoreboard = new JButton("View Scoreboard");
        JPanel viewScoreboardBuffer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewScoreboardBuffer.add(viewScoreboard);

        // Arrange the components in a grid.
        JPanel grid = new JPanel(new GridLayout(4, 1, 5, 5));
        grid.add(titleLabel);
        grid.add(loginButtonBuffer);
        grid.add(exitButtonBuffer);
        grid.add(viewScoreboardBuffer);

        setPreferredSize(new Dimension(400,200));

        this.add(grid);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Create the window.
        JFrame frame = new JFrame("Menu Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the MenuPanel instance to the frame.
        MenuControl mc = new MenuControl();
        MenuPanel menuPanel = new MenuPanel(mc);
        frame.getContentPane().add(menuPanel);

        // Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



}
