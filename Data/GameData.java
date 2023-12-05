package Data;

import java.io.*;
import Utility.ShipGrid;
import Utility.ShootGrid;

public class GameData implements Serializable{

	private ShipGrid shipGrid;
	private ShootGrid shootGrid;
	private int[] target;
	private String feedback;
	// private Feedback detailed_feedback;
	private String turn;
	private String detailed_feedback = "";
	private String type;
	
	public ShipGrid getShipGrid()
	{
		return shipGrid;
	}
	
	public ShootGrid getShootGrid()
	{
		return shootGrid;
	}
	
	public int[] getTarget()
	{
		return target;
	}
	
	public void setShipGrid(ShipGrid shipGrid)
	{
		this.shipGrid = shipGrid;
	}
			
	public void setShootGrid(ShootGrid shootGrid)
	{
		this.shootGrid = shootGrid;
	}
	
	public void setTarget(int[] target)
	{
		this.target = target;
	}

	//3 types of messages... feedback (for the player), detailed feedback (for the player), turn (for player), type (for client)
	public void setFeedback(String feedback){
		this.feedback = feedback;
	}
	
	public String getFeedback(){
		return this.feedback;
	}

	public void setDetailedFeedback(String detailed_feedback){
		this.detailed_feedback = detailed_feedback;
	}

	public String getDetailedFeedback(){
		return this.detailed_feedback;
	}

	public void setTurn(String turn){
		this.turn = turn;
	}

	//keep track of turn 
	public String getTurn(){
		return this.turn;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	//constructor
	public GameData(ShipGrid shipGrid, ShootGrid shootGrid)
	{
		setShipGrid(shipGrid);
		setShootGrid(shootGrid);
		this.target = new int[2];
		// detailed_feedback = new Feedback("", "");
	}
}
