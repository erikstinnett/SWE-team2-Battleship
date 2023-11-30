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
	private Database db;
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
		log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");

		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData) {
			// Check the username and password with the database.
			LoginData data = (LoginData) arg0;
			Object result;
			String username = data.getUsername();
			String password = data.getPassword();
			String query = "select aes_decrypt(password,'key') from user where username='" + username + "'";

			if (password.equals(db.queryCheckPassword(query))) {
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
				dml += password + "','key'))";

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

				// create gameroom, or join one that's already made
				if (gameRoom.isEmpty()) { // player 1
					single_gameRoom = new GameRoom(arg1);
					gameRoom.add(single_gameRoom);
					rNum = 0;
					sentence = "You are player 1\nWaiting for Opponent";
					type = "CreateGameWait";
				} else {
					Boolean addnew = true;
					for (int i = 0; i < gameRoom.size(); i++) {
						if (!gameRoom.get(i).isFull()) {
							gameRoom.get(i).setPlayer2(arg1); // player 2
							addnew = false;
							rNum = i;
							sentence = "You are player 2";
							type = "CreateGameReady";
						}
						if (addnew) {
							single_gameRoom = new GameRoom(arg1);
							gameRoom.add(single_gameRoom); // player 2
							rNum = gameRoom.get(gameRoom.size() - 1);
							sentence = "You are player 2";
							type = "CreateGameReady";
						}
					}
				}

				// if (gameRoom.get(rNum).getPlayer1() != null){
				// gameRoom.get(rNum).setPlayer2(arg1);
				// //get message
				// sentence = "You are player 2";
				// type = "CreateGameWait";
				// }
				// else{
				// gameRoom.add(new GameRoom(arg1));
				// //get message
				// sentence = "You are player 1\nWaiting for Opponent";
				// type = "CreateGameReady";
				// }

				Object result = new Feedback(sentence, type);

				// Send the result to the client.
				// grab the players ConnectionToClient object
				ConnectionToClient player_1;
				ConnectionToClient player_2;
				// player_1 = gameRoom.get(rNum).getPlayer1();
				// player_2 = gameRoom.get(rNum).getPlayer2();

				try {
					// send to player 1
					if (feedback.getType().equals("CreateGameWait")) {
						player_1 = gameRoom.get(rNum).getPlayer1();
						player_1.sendToClient(result);
					}
					// send to player 2
					else if (feedback.getType().equals("CreateGameReady")) {
						player_1 = gameRoom.get(rNum).getPlayer1();
						player_2 = gameRoom.get(rNum).getPlayer2();

						player_1.sendToClient(result);
						player_2.sendToClient(result);
					}

				} catch (IOException e) {
					return;
				}

			}

			// If ScoreBoarddata
			else if (feedback.getType().equals("ScoreBoardData")) {
				// implement

				// determine the player
				int gameRoomCount = 0;
				String whichPlayer;

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
				// assign the roomnumber for game room
				int rNum = gameRoomCount;

				String player_username;

				if (whichPlayer.equals("Player 1")) {

					player_username = gameRoom.get(rNum).getPlayer1Username();
				} else {
					player_username = gameRoom.get(rNum).getPlayer2Username();
				}

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

			// Create Game data
			startofGameData = (StartofGameData) arg0;

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

			// Send board data to the player
			try {
				arg1.sendToClient(gameData);
			} catch (IOException e) {
				return;
			}

		}

		else if (arg0 instanceof GameData) {
			// implement

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

			// game data
			gameData = (GameData) arg0;
			int[] target = gameData.getTarget();

			//Construct Feedback 
			Feedback feedback = null;

			//see if hit or miss, and construct feedback. Also get the fleet of ships
			if (whichPlayer.equals("Player 1")){
				//get player 2 shipgrid
				shipGrid = gameRoom.get(rNum).getPlayer2ShipGrid();

				feedback = shipGrid.update(target);
			}
			else { //player 2
				//get player 1 shipgrid
				shipGrid = gameRoom.get(rNum).getPlayer1ShipGrid();

				feedback = shipGrid.update(target);
			}


			// This will test to see if any ships have lives left to see if the ATTACKER won
			ships = shipGrid.getShips();

			if (ships.get(0).isSunk() && ships.get(1).isSunk() && ships.get(2).isSunk() && ships.get(3).isSunk() && ships.get(4).isSunk()) {
				//overwrite feedback
				feedback = new Feedback("You win! \nYou sunk the opponent's fleet.", "GameOver");
			}

			ConnectionToClient player_1 = gameRoom.get(rNum).getPlayer1();
			ConnectionToClient player_2 = gameRoom.get(rNum).getPlayer2();

			// Send the result to the client.
			try {
				//in the instance the ATTACKER wins
				if (feedback.getType().equals("GameOver")){
					arg1.sendToClient(feedback);

					//reassign feedback for the losing opponent
					feedback = new Feedback("You lost", "GameOver");
					if (whichPlayer.equals("Player 1"))
						player_2.sendToClient(feedback);
					else
						player_1.sendToClient(feedback);
				}
				//Otherwise, send feedback regarding their attack
				else{
					arg1.sendToClient(feedback); //hit, miss
				}
			} catch (IOException e) {
				return;
			}

		}

		else if (arg0 instanceof EndofGameData) {
			// implement

			// end of game data
			endofGameData = (EndofGameData) arg0;

			// determine the player
			int gameRoomCount = 0;
			String whichPlayer;

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
			// assign the roomnumber for game room
			int rNum = gameRoomCount;

			String player_username;

			if (whichPlayer.equals("Player 1")) {

				player_username = gameRoom.get(rNum).getPlayer1Username();
			} else {
				player_username = gameRoom.get(rNum).getPlayer2Username();
			}

			// determine if win/loss and add the result to wins/losses
			String dml;

			if (endofGameData.isWin())
				dml = "update gameData set wins = wins + 1 where name = \"" + player_username + "\";";
			else
				dml = "update gameData set losses = losses + 1 where name = \"" + player_username + "\";";

			// update database
			db.executeDML(dml);

			String sentences = "Not implemented yet";

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
