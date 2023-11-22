package Utility;

import java.io.Serializable;

public class Feedback implements Serializable
{
  // Data field for storing the feedback message and type.
  private String message;
  private String type;
  
  // Getters for the feedback message and type.
  public String getMessage()
  {
    return message;
  }
  public String getType()
  {
    return type;
  }
  
  // Setters for the feedback message and type.
  public void setMessage(String message)
  {
    this.message = message;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  
  // Constructor for creating a new feedback object with a message and type.
  public Feedback(String message, String type)
  {
    setMessage(message);
    setType(type);
  }
}
