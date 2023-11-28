package Panel;

import javax.swing.*;

import Controller.EndofGameControl;

import java.awt.*;

public class EndGamePanel extends JPanel {
    private EndofGameControl control;

    public EndGamePanel(EndofGameControl egc) {
        this.control = egc; // Store the reference to the EndGameControl object
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Determine the winner and create a message
        String resultText = control.didPlayerWin() ? "Congratulations, You Won!" : "You Lost, Opponent Won!";
        JLabel resultLabel = new JLabel(resultText, JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 24));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Replay button
        JButton replayButton = new JButton("Play Again");
        replayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        replayButton.addActionListener(e -> control.restartGame());

        // Exit button
        JButton exitButton = new JButton("Exit to Main Menu");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> control.exitToMainMenu());

        // Add components to the panel
        add(Box.createVerticalGlue());
        add(resultLabel);
        add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        add(replayButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        add(exitButton);
        add(Box.createVerticalGlue());
    }

    // Main method for testing the panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("End Game Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new EndGamePanel(new EndofGameControl()));
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }

}

//mock EGC for testing..
class EndGameControl {
    public boolean didPlayerWin() {
        // Logic to determine if the player won
        return true; // Placeholder for actual win determination logic
    }

    public void restartGame() {
        // Logic to restart the game
        System.out.println("Restarting game...");
    }

    public void exitToMainMenu() {
        // Logic to exit to the main menu
        System.out.println("Exiting to the main menu...");
    }

}

