package DataPackage;

import java.io.*;

public class LoginData implements Serializable 
{
  private String username;
  private String password;
  
  
  public String Username()
  {
    return username;
  }
  public String Password()
  {
    return password;
  }
  
  public void getUsername(String username)
  {
    this.username = username;
  }
  public void getPassword(String password)
  {
    this.password = password;
  }
  
  public LoginData(String username, String password)
  {
    getUsername(username);
    getPassword(password);
  }
}
