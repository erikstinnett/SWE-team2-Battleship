package Server;

//import package 
import Data.*;
//
import Utility.*;
import Utility.Error;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameServer extends AbstractServer {
	// Labels for ServerGUI
	private JTextArea log;
	private JLabel status;

	// DATA
	Database db;
	private LoginData loginData;
	private CreateAccountData createAccountData;
	private StartofGameData startofGameData;
	private GameData gameData;
	private EndofGameData endofGameData;

	// Utilities
	// private GameRoom[] gameRoom = new GameRoom[10];
	private ArrayList<GameRoom> gameRoom = new ArrayList<GameRoom>();
	private GameRoom single_gameRoom;

	// grid
	private int[][] shootGridarr;
	private int[][] shipGridarr;
	private int[][] localShootgridarr; // REVISE
	private ShipGrid shipGrid;

	// coordinates
	private int[] lastShot;
	private int[] coords;

	// ships
	private ArrayList<Ship> ships;
	private Ship ship;

	// other utilities for mini tasks
	private ArrayList queryResults;

	public void startDatabase(){
		db = new Database();
		
		db.setConnection("./SWE-team2-Battleship/db.properties");
        
	}

	public void setDatabase(Database db) {
		this.db = db;
	}

	public GameServer() {
		super(12345);
	}

	public GameServer(int port) {
		super(port);
	}

	public void setLog(JTextArea log) {
		this.log = log;
	}

	public JTextArea getLog() {
		return log;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}

	public JLabel getStatus() {
		return status;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		System.out.println("Message from Client " + arg0.toString() + arg1.toString());
		log.append("Message from Client " + arg0.toString() + arg1.toString() + "\n");

		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData) {
			// Check the username and password with the database.
			LoginData data = (LoginData) arg0;
			Object result;
			String username = data.getUsername();
			String password = data.getPassword();
			String query = "select aes_decrypt(password,'key') from user where username='" + username + "'";

			if (!password.equals(db.queryCheckPassword(query))) {
				result = new Feedback(data.getUsername(), "LoginSuccessfull");
				log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
			} else {
				result = new Error("The username and password are incorrect.", "Login");
				log.append("Client " + arg1.getId() + " failed to log in\n");
			}

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
			} catch (IOException e) {
				return;
			}
		}

		// If we received CreateAccountData, create a new account.
		else if (arg0 instanceof CreateAccountData) {
			// Try to create the account.
			CreateAccountData data = (CreateAccountData) arg0;
			Object result = "";
			String username = data.getUsername();
			String password = data.getPassword();

			// String passwordForVerif = data.getPasswordForVerification();
			String query = "select username from user where username ='" + username + "'";

			// if NULL, then create user
			if (db.query(query).equals(null)) {
				// insert the username and password
				String dml = "insert into user values('";
				dml += username + "',aes_encrypt('";
				dml += password + "','key'),";
				dml += "0,0);";

				// execute DML onto db
				try {
					db.executeDML(dml);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				result = "CreateAccountSuccessful";
				log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
			} else {
				result = new Error("Username has already been selected", "CreateAccount");
				log.append("Client " + arg1.getId() + " failed to create a new account\n");
			}

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
			} catch (IOException e) {
				return;
			}

		}

		// NOTES
		// will have to figure out how to keep gameRoom count! REVISE
		// will gameRoom actually be able to support multiple other objects?

		else if (arg0 instanceof Feedback) {
			// implement String function

			// get the Feedback object
			Feedback feedback = (Feedback) arg0;

			// If player is joining a game
			if (feedback.getType().equals("CreateGame")) {

				// room number
				int rNum = 0;

				// Create some kind of feedback to the player 1/2
				String sentence = "";
				String type = "";

				//For creating new room
				Boolean addnew = true;

				// create gameroom, or join one that's already made
				if (gameRoom.isEmpty()) { // player 1 presses PLAY!
					single_gameRoom = new GameRoom(arg1);
					gameRoom.add(single_gameRoom);
					rNum = 0;
					// sentence = "You are player 1\nWaiting for Opponent";
					type = "CreatedGame";
					addnew = false;
				} 
				else {
					for (int i = 0; i < gameRoom.size(); i++) {
						if (gameRoom.get(i).getPlayer1().equals(arg1)){ //player 1 confirmed ship placement
							sentence = "You are player 1\nWaiting for Opponent";
							type = "CreateGameWait";
							addnew = false;
							break;
						}
						if (!gameRoom.get(i).isFull()) {
							gameRoom.get(i).setPlayer2(arg1); // player 2 presses PLAY!
							addnew = false;
							rNum = i;
							// sentence = "You are player 2";
							type = "JoinedGame";
							break;
						}
						if (gameRoom.get(i).getPlayer2().equals(arg1)){
							gameRoom.get(i).setPlayer2(arg1); // player 2 confirmed ship placement
							addnew = false;
							rNum = i;
							sentence = "You are player 2";
							type = "CreateGameReady";
							break;
						}
					}
				}

				if (addnew) { //IF ALL ROOMS ARE FULL, CREATE A NEW ONE!
					single_gameRoom = new GameRoom(arg1);
					gameRoom.add(single_gameRoom); // player 2
					rNum = gameRoom.size() - 1;
					sentence = "You are player 2";
					type = "CreatedGame";
				}

				feedback = new Feedback(sentence, type);

				// // Send the result to the client.
				// // grab the players ConnectionToClient object
				// ConnectionToClient player_1;
				// ConnectionToClient player_2;

				try {
					// send to player 1/2 when they are in a room
					if (feedback.getType().equals("CreatedGame") ||  
						feedback.getType().equals("JoinedGame")) {
							arg1.sendToClient(feedback);
					}
					// send to player 1 to let them know they are waiting...
					else if (feedback.getType().equals("CreateGameWait")) {
						arg1.sendToClient(feedback);
					}
					// send to player 2, who will then send "PlayGame", which can be seen below!!
					else if (feedback.getType().equals("CreateGameReady")){
						arg1.sendToClient(feedback);
					}

				} catch (IOException e) {
					return;
				}

			}

			//Player 2 has confirmed ship placement, now the server will get a response that happens, 
			//which then it will cause both players to send their sog data
			else if (feedback.getType().equals("PlayGame")){
				// get player 2
				int gameRoomCount = 0;

				// Get player 2 game room
				for (int i = 0; i < gameRoom.size(); i++) {
					if (gameRoom.get(i).getPlayer2().equals(arg1)) {
						gameRoomCount = i;
						break;
					}
				}
				// assign the roomnumber for game room
				int rNum = gameRoomCount;


				// Send the result to the client.
				// grab the players ConnectionToClient object
				ConnectionToClient player_1 = gameRoom.get(rNum).getPlayer1();
				ConnectionToClient player_2 = gameRoom.get(rNum).getPlayer2();

				//request player 1 and 2 to send sogData
				try {
					feedback = new Feedback("", "SendSOGData");
					player_1.sendToClient(feedback);
					player_2.sendToClient(feedback);
				} catch (Exception e) {
					return;
				}

			}

			// If ScoreBoarddata
			else if (feedback.getType().equals("ScoreBoardData")) {
				// implement

				// // determine the player
				// int gameRoomCount = 0;
				// String whichPlayer = "";

				// // Test which gameroom to use...
				// for (int i = 0; i < gameRoom.size(); i++) {
				// 	if (gameRoom.get(i).getPlayer1().equals(arg1) || gameRoom.get(i).getPlayer2().equals(arg1)) {
				// 		if (gameRoom.get(i).getPlayer1().equals(arg1)) {
				// 			whichPlayer = "Player 1";
				// 		} else {
				// 			whichPlayer = "Player 2";
				// 		}
				// 		gameRoomCount = i;
				// 		break;
				// 	}
				// }
				// // assign the roomnumber for game room
				// int rNum = gameRoomCount;

				// String player_username;

				// if (whichPlayer.equals("Player 1")) {

				// 	player_username = gameRoom.get(rNum).getPlayer1Username();
				// } else {
				// 	player_username = gameRoom.get(rNum).getPlayer2Username();
				// }

				//player username
				String player_username = feedback.getMessage();

				// query for getting the player's username
				String query_get_player = "select username,wins,losses from gameData where name = \"" + player_username
						+ "\";";

				// query for getting top 5 players
				String query_get_top_5 = "select username,wins,losses from gameData order by wins desc limit 5;";

				// get the player, and top 5, and add them to queryResults
				queryResults.addAll(0, db.query(query_get_player));
				queryResults.addAll(1, db.query(query_get_top_5));

				// Send the queryREsults.
				try {
					arg1.sendToClient(queryResults);
				} catch (IOException e) {
					return;
				}

			}

			// Scoreboarddata here? REVISE
		}

		else if (arg0 instanceof StartofGameData) {
			// implement
			// The server will let the client know when

			try {
				// Create Game data
				startofGameData = (StartofGameData) arg0;

				// //for logs...
				// log.append("Message from Client " + arg0.toString() + " " + startofGameData.getPlayerUsername() + "\n");

				gameData = new GameData(startofGameData.getShipGrid(), startofGameData.getShootGrid());

				// Assign each players grid for the Server's reference
				int gameRoomCount = 0;
				String whichPlayer = "";

				// Test which gameroom to use...
				for (int i = 0; i < gameRoom.size(); i++) {
					if (gameRoom.get(i).getPlayer1().equals(arg1) || gameRoom.get(i).getPlayer2().equals(arg1)) {
						if (gameRoom.get(i).getPlayer1().equals(arg1)) {
							whichPlayer = "Player 1";
							gameRoom.get(i).setPlayer1Username(startofGameData.getPlayerUsername());
						} else {
							whichPlayer = "Player 2";
							gameRoom.get(i).setPlayer1Username(startofGameData.getPlayerUsername());
						}
						gameRoomCount = i;
						break;
					}
				}

				int rNum = gameRoomCount;
				if (whichPlayer.equals("Player 1")) {
					gameRoom.get(rNum).setPlayer1Boards(startofGameData.getShipGrid(), startofGameData.getShootGrid());
				} else {
					gameRoom.get(rNum).setPlayer2Boards(startofGameData.getShipGrid(), startofGameData.getShootGrid());
				}

				//Decide turn order...
				if (whichPlayer.equals("Player 1")) {
					// if (gameRoom.get(rNum).getPlayer2Username().isEmpty())
					gameData.setTurn("Your turn");
					gameData.setType("InitialPlayerTurn");
				}
				else{
					gameData.setTurn("Opponent turn");
					gameData.setType("InitialPlayerTurn");
				}
				// } else {
				// 	if (gameRoom.get(rNum).getPlayer1Username().isEmpty())
				// 		gameData.setFeedback("Your turn after opponent sets their board");
				// }

				// Send board data to the player
				try {
					arg1.sendToClient(gameData);
				} catch (IOException e) {
					return;
				}
			} 

			//EXCEPTION OCCURS
			catch (Exception e){
				Error err = new Error("Player left", "PlayerLeft");

				try {
					arg1.sendToClient(err);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}

			}

		}

		else if (arg0 instanceof GameData) {
			
			// determine the player
			int gameRoomCount = 0;
			String whichPlayer = "";

			// Test which gameroom to use...
			for (int i = 0; i < gameRoom.size(); i++) {
				if (gameRoom.get(i).getPlayer1().equals(arg1) || gameRoom.get(i).getPlayer2().equals(arg1)) {
					if (gameRoom.get(i).getPlayer1().equals(arg1)) {
						whichPlayer = "Player 1";
					} else {
						whichPlayer = "Player 2";
					}
					gameRoomCount = i;
					break;
				}
			}

			//room number
			int rNum = gameRoomCount;

			// //for logs...
			// log.append("Message from Client " + arg0.toString() + " " + gameRoom.get(rNum).getPlayer1Username() + "\n");

			// game data
			gameData = (GameData) arg0;
			int[] target = gameData.getTarget();

			//Construct Feedback 
			Feedback feedback = null;

			//see if hit or miss, and construct feedback. Also get the fleet of ships, and update the gameRoom
			if (whichPlayer.equals("Player 1")){
				//get player 2 shipgrid
				shipGrid = gameRoom.get(rNum).getPlayer2ShipGrid();
				feedback = shipGrid.update(target);
				//update gameData to send back
				gameData.setShipGrid(shipGrid);
				//update gameRoom 
				gameRoom.get(rNum).setPlayer2ShipGrid(shipGrid);
			}
			else { //player 2
				//get player 1 shipgrid
				shipGrid = gameRoom.get(rNum).getPlayer1ShipGrid();
				feedback = shipGrid.update(target);
				gameData.setShipGrid(shipGrid);
				gameRoom.get(rNum).setPlayer1ShipGrid(shipGrid);
			}

			//log
			log.append(whichPlayer + " sent an attack\n" + ": From " + arg1.toString());

			//set the feedback for gamedata
			gameData.setFeedback(feedback.getMessage());
			gameData.setDetailedFeedback(feedback.getType());
			if (!feedback.getDetailedMessage().isEmpty())
				gameData.setDetailedFeedback(feedback.getDetailedMessage());

			//switch turn order
			gameData.setTurn("Opponent turn");
			// if (gameData.getTurn().equals("Your turn"))
			// 	gameData.setTurn("Opponent turn");
			// else
			// 	gameData.setTurn("Your turn");

			gameData.setType("PlayerTurn");

			// This will test to see if any ships have lives left to see if the ATTACKER won
			ships = shipGrid.getShips();

			if (ships.get(0).isSunk() && ships.get(1).isSunk() && ships.get(2).isSunk() && ships.get(3).isSunk() && ships.get(4).isSunk()) {
				//overwrite feedback
				// gameData.setFeedback("You win! \nYou sunk the opponent's fleet.");
				feedback.setMessage("You win!");
				feedback.setType("EndofGame");
			}
				
			// feedback = new Feedback("You win! \nYou sunk the opponent's fleet.", "GameOver");
			

			ConnectionToClient player_1 = gameRoom.get(rNum).getPlayer1();
			ConnectionToClient player_2 = gameRoom.get(rNum).getPlayer2();

			// Send the result to the client.
			try {
				//in the instance the ATTACKER wins
				if (feedback.getType().equals("EndofGame")){
					// arg1.sendToClient(gameData);
					arg1.sendToClient(feedback);
					//reassign feedback for the losing opponent
					feedback.setMessage("You lost!");
					if (whichPlayer.equals("Player 1"))
						player_2.sendToClient(feedback);
					else
						player_1.sendToClient(feedback);
				}
				//Otherwise, send feedback regarding their attack
				else{
					//send game data to player (needs shootgrid)
					arg1.sendToClient(gameData);
					//send game data to opponent (needs shootgrid, and detailedMessage if any)
					gameData.setTurn("Your turn");
					if (whichPlayer.equals("Player 1")){
						player_2.sendToClient(gameData);
						// player_2.sendToClient(feedback);
					}	
					else{
						player_1.sendToClient(gameData);
						// player_1.sendToClient(feedback);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

		}

		else if (arg0 instanceof EndofGameData) {
			// implement

			// end of game data
			endofGameData = (EndofGameData) arg0;

			String player_username = endofGameData.getPlayerUsername();
			

			// determine if win/loss and add the result to wins/losses
			String dml = "";
			String sentences = "";

			if (endofGameData.isWin()){
				dml = "update gameData set wins = wins + 1 where name = \"" + player_username + "\";";
				sentences = "You win!";
			}
				
			else{
				dml = "update gameData set losses = losses + 1 where name = \"" + player_username + "\";";
				sentences = "You lost!";
			}
				

			// // update database
			// try {
			// 	db.executeDML(dml);
			// } catch (SQLException e) {
			// 	// TODO Auto-generated catch block
			// 	//e.printStackTrace();
			// }

			// construct message
			Object result;
			result = new Feedback(sentences, "EndofGame");

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
			} catch (IOException e) {
				return;
			}

		}


	}

	protected void listeningException(Throwable exception) {
		// Display info about the exception
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		if (this.isListening()) {
			log.append("Server not Listening\n");
			status.setText("Not Connected");
			status.setForeground(Color.RED);
		}

	}

	protected void serverStarted() {
		System.out.println("Server Started");
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server Started\n");
	}

	protected void serverStopped() {
		System.out.println("Server Stopped");
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}

	protected void serverClosed() {
		System.out.println("Server and all current clients are closed - Press Listen to Restart");
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
	}

	protected void clientConnected(ConnectionToClient client) {
		// Create User id
		Long id = client.getId();
		String userID = id.toString();
		// user_id = new User(userID);

		System.out.println("Client Connected");
		log.append("Client Connected\n");
	}

}
