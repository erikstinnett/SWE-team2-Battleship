package Data;

import java.io.*;

public class EndofGameData implements Serializable {

	private Boolean win;
	private String username;

	public EndofGameData(Boolean win){
		this.win = win;
	}
	  
	//revise
	public Boolean isWin(){
		return win;
	}	
	
	public void setPlayerUsername(String username){
		this.username = username;
	}

	public String getPlayerUsername(){
		return this.username;
	}
}
