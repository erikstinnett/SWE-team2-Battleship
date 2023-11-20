package DataPackage;

import java.io.*;

public class EndofGameData {

	private String winerUser;
	private String loserUser;
	  
	  public String winerUser()
	  {
	    return winerUser;
	  }
	  public String loserUser()
	  {
	    return loserUser;
	  }
	  
	  public void getwinerUser(String winerUser)
	  {
	    this.winerUser = winerUser;
	  }
	  public void getloserUser(String passwordloserUser)
	  {
	    this.loserUser = loserUser;
	  }
	  
	  public EndofGameData(String winerUser, String loserUser)
	  {
	    getwinerUser(winerUser);
	    getloserUser(loserUser);
	  }
	  
	  
	  
	  
	  
}
