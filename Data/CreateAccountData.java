package Data;

import java.io.Serializable;

public class CreateAccountData implements Serializable {
// Private data fields for the username and password.
  private String username;
  private String password;
  private String verifiedPw;
  
  // Getters for the username and password.
  public String getUsername()
  {
    return username;
  }
  public String getPassword()
  {
    return password;
  }
  public String getPasswordForVerification(){
    return verifiedPw;
  }
  
  // Setters for the username and password.
  public void setUsername(String username)
  {
    this.username = username;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }

  public void setPasswordForVerification(String verifiedPw){
    this.verifiedPw = verifiedPw;
  }
  
  // Constructor that initializes the username and password.
  public CreateAccountData(String username, String password, String verifiedPw)
  {
    setUsername(username);
    setPassword(password);
    setPasswordForVerification(verifiedPw);
  }
}
