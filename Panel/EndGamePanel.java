package Panel;

import javax.swing.*;

import Controller.EndofGameControl;

import java.awt.*;

public class EndGamePanel extends JPanel {
    private EndofGameControl control;
    private JLabel resultLabel;

    public EndGamePanel(EndofGameControl egc) {
        this.control = egc; // Store the reference to the EndGameControl object
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Determine the winner and create a message
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 24));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Replay button
        JButton replayButton = new JButton("Play Again");
        replayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        replayButton.addActionListener(egc);

        // Exit button
        JButton exitButton = new JButton("Exit to Main Menu");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(egc);

        // Add components to the panel
        add(Box.createVerticalGlue());
        add(resultLabel);
        add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        add(replayButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        add(exitButton);
        add(Box.createVerticalGlue());
    }
    
    public void setResult(String result) {
    	resultLabel.setText(result);
    }
}
