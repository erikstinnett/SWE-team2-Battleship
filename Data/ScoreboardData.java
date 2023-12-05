package Data;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreboardData implements Serializable{

	private ArrayList<String> sb;
	
	public ScoreboardData(ArrayList<String> sb) {
		this.sb = sb;
	}
	
	public ArrayList<String> getSb(){
		return sb;
	}
	
}
