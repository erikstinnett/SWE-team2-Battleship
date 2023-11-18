package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class InitialControl implements ActionListener{
	
	private JPanel container;
	
	InitialControl(JPanel container){
		this.container = container;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action.equals("Login")) {
			
		}
		else if (action.equals("Create Account")) {
			
		}
	}

}
