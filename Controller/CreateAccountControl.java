package Controller;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener{

	private JPanel container;
	private Object gameClient;
	
	CreateAccountControl(JPanel container, Object gameClient){
		this.container = container;
		this.gameClient = gameClient;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void invalidAccount(String error){
		
	}
	
	public void createAccountSuccess() {
		
	}

}
