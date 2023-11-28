package Utility;

import java.io.Serializable;

public class Error implements Serializable
{
  // Data field for storing the error message and type.
  private String message;
  private String type;
  
  // Getters for the error message and type.
  public String getMessage()
  {
    return message;
  }
  public String getType()
  {
    return type;
  }
  
  // Setters for the error message and type.
  public void setMessage(String message)
  {
    this.message = message;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  
  // Constructor for creating a new Error object with a message and type.
  public Error(String message, String type)
  {
    setMessage(message);
    setType(type);
  }
}
