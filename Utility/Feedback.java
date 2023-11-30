package Utility;

import java.io.Serializable;

public class Feedback implements Serializable
{
  // Data field for storing the feedback message and type.
  private String message;
  private String type;
  // this field is for detailed messages, e.g., which ship was sunk
  private String detailed_msg;
  
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

  // Setter and Getter for extra detailed message
  public void setDetailedMessage(String detailed_msg){
    this.detailed_msg = detailed_msg;
  }

  public String getDetailedMessage(){
    return this.detailed_msg;
  }
  
  // Constructor for creating a new feedback object with a message and type.
  public Feedback(String message, String type)
  {
    setMessage(message);
    setType(type);
  }
}
