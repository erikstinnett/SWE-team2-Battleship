package Data;

import java.io.*;

public class EndofGameData {

	private String winerUser;
	private String loserUser;
	  
	  public String getwinerUser()
	  {
	    return winerUser;
	  }
	  public String getloserUser()
	  {
	    return loserUser;
	  }
	  
	  public void setwinerUser(String winerUser)
	  {
	    this.winerUser = winerUser;
	  }
	  public void setloserUser(String loserUser)
	  {
	    this.loserUser = loserUser;
	  }
	  
	  public EndofGameData(String winerUser, String loserUser)
	  {
	    setwinerUser(winerUser);
	    setloserUser(loserUser);
	  }
	  
	  
	  
	  
	  
}
