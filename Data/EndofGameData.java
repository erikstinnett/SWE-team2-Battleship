package Data;

import java.io.*;

public class EndofGameData implements Serializable {

	private Boolean win;

	public EndofGameData(Boolean win){
		this.win = win;
	}
	  
	//revise
	public Boolean isWin(){
		return win;
	}	  
}
