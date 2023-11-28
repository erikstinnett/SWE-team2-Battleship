package Data;

import java.io.*;

public class LoginData implements Serializable 
{
  private String username;
  private String password;
  
  
  public String getUsername()
  {
    return username;
  }
  public String getPassword()
  {
    return password;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public LoginData(String username, String password)
  {
    setUsername(username);
    setPassword(password);
  }
}
