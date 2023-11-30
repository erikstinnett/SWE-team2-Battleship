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

			// //Error check
			// Boolean NoCredError = true;

			// if (username.equals("")||password.equals("")){
			// result = new Error("You must enter a username and password",
			// "CreateAccount");
			// log.append("Client " + arg1.threadId() + " failed to create a new
			// account\n");
			// NoCredError = false;
			// }
			// else if(username.length() < 6){
			// result = new Error("Username must be at least 6 characters",
			// "CreateAccount");
			// log.append("Client " + arg1.threadId() + " failed to create a new
			// account\n");
			// NoCredError = false;
			// }
			// else if(!password.equals(passwordForVerif)){
			// result = new Error("Passwords must match", "CreateAccount");
			// log.append("Client " + arg1.threadId() + " failed to create a new
			// account\n");
			// NoCredError = false;
			// }

			// if (NoCredError) {
			// if (!db.query(query)) { //if NOT exists... create! (NoCredError has to be
			// TRUE=no error)
			// //insert the username and password
			// String dml = "insert into user values('";
			// dml += username + "',aes_encrypt('";
			// dml += password + "','key'))";

			// // execute DML onto db
			// try {
			// db.executeDML(dml);
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// result = "CreateAccountSuccessful";
			// log.append("Client " + arg1.threadId() + " created a new account called " +
			// data.getUsername() + "\n");
			// }
			// else
			// {
			// result = new Error("Username has already been selected", "CreateAccount");
			// log.append("Client " + arg1.threadId() + " failed to create a new
			// account\n");
			// }
			// }

			// // Send the result to the client.
			// try
			// {
			// arg1.sendToClient(result);
			// }
			// catch (IOException e)
			// {
			// return;
			// }

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

			// game data
			gameData = (GameData) arg0;

			// update their grids, and grab the other player's board..
			// ShipGrid shipGrid = gameData.getShipGrid();
			ShootGrid shootGrid = gameData.getShootGrid();
			int rNum = gameRoomCount;

			// Get grid arrays
			shootGridarr = shootGrid.getGrid(); // ATTACKER
			ShipGrid shipGrid;
			// Is the attacker's last shot null? TRUE/FALSE
			Boolean attackerLastShotisNull;
			// Grab attackee ship grid
			if (whichPlayer.equals("Player 1")) {
				shipGrid = gameRoom.get(rNum).getPlayer2ShipGrid(); // DEFENDER
				shipGridarr = shipGrid.getGridasArray();
				// are their last shot null?
				if (gameRoom.get(rNum).getPlayer1LastShot().equals(null)) {
					attackerLastShotisNull = true;
				} else {
					attackerLastShotisNull = false;
				}
			} else {
				shipGrid = gameRoom.get(rNum).getPlayer1ShipGrid(); // DEFENDER
				shipGridarr = shipGrid.getGridasArray();

				if (gameRoom.get(rNum).getPlayer2LastShot().equals(null)) {
					attackerLastShotisNull = true;
				} else {
					attackerLastShotisNull = false;
				}
			}

			// Assign the shootGrid coord to see if it hit a ship or missed
			// if null
			if (attackerLastShotisNull) {
				for (int y = 0; y < shootGridarr.length; y++) { // y coord
					for (int x = 0; x < shootGridarr[y].length; x++) {// x coord

						if (shootGridarr[y][x] == 1) { // this means that an shootgrid attack pin has been found
							lastShot = new int[2];
							lastShot[0] = y;
							lastShot[1] = x;
							if (whichPlayer.equals("Player 1")) {
								gameRoom.get(rNum).setPlayer1LastShot(lastShot); // store the last shot
								break;
							} else { // player 2
								gameRoom.get(rNum).setPlayer2LastShot(lastShot); // store the last shot
								break;
							}

						}
					}
				}
			}
			// else update lastshot
			else {
				for (int y = 0; y < shootGridarr.length; y++) { // y coord
					for (int x = 0; x < shootGridarr[y].length; x++) {// x coord

						if (shootGridarr[y][x] == 1) { // this means that an shootgrid attack pin has been found
							lastShot = new int[2];
							lastShot[0] = y;
							lastShot[1] = x;
							if (whichPlayer.equals("Player 1")) {
								if (!gameRoom.get(rNum).getPlayer1LastShot().equals(lastShot)) { // previous shot !=
																									// last shot
									gameRoom.get(rNum).setPlayer1LastShot(lastShot);
								}
							} else { // player 2
								if (!gameRoom.get(rNum).getPlayer2LastShot().equals(lastShot)) { // previous shot !=
																									// last shot
									gameRoom.get(rNum).setPlayer2LastShot(lastShot);
								}
							}
						}
					}
				}
			}

			// Find the matching pins from the ATTACKER shootgrid to the DEFENDER shipgrid
			// Get opponents ships to do further action!
			ships = shipGrid.getShips(); // DEFENDER ships
			Boolean hit_marker = false;
			Ship hit_ship = new Ship("temp", 0);

			// loop over DEFENDER ships and see if ATTACKER lastshot matches their
			// coordinates
			for (int shipIndex = 0; shipIndex < ships.size(); shipIndex++) {
				// get ship
				ship = ships.get(shipIndex);

				// get ship coordinates
				coords = ship.getCoordinates();
				int ship_y1_coord = coords[0];
				int ship_x1_coord = coords[1];
				int ship_y2_coord = coords[2];
				int ship_x2_coord = coords[3];
				int attack_y_coord = lastShot[0];
				int attack_x_coord = lastShot[1];

				if (ship_y1_coord <= attack_y_coord && attack_y_coord <= ship_y2_coord) {
					if (ship_x1_coord <= attack_x_coord && attack_x_coord <= ship_x2_coord) {

						ship.removeLife(); // new method

						// notify that there was a hitmarker
						hit_marker = true;
						// assign the hurt ship
						hit_ship = ship;
						break;

					}
				}

			}

			// Build feedback to send to client
			Object result;
			String sentences;

			// REVISE: OR THIS COULD CLONE THE GAMEDATA SHOOTGRID ARRAY !!
			// update player's gameroom shootgrid to include lastshot
			if (whichPlayer.equals("Player 1")) {
				// update shootgrid
				gameRoom.get(rNum).updatePlayer1ShootGrid(lastShot);
				// if a hit/miss, update shipgrid
				if (hit_marker) {
					sentences = "Your opponent hit your " + hit_ship.getName();
					// will have to find a way to show that the ship has been hit...
				} else {
					sentences = "Your opponent missed you";
					gameRoom.get(rNum).updatePlayer1ShipGridWithShots(lastShot, "miss");
				}
			} else {
				gameRoom.get(rNum).updatePlayer2ShootGrid(lastShot);
				if (hit_marker) {
					sentences = "Your opponent hit your " + hit_ship.getName();
				} else {
					sentences = "Your opponent missed you";
					gameRoom.get(rNum).updatePlayer2ShipGridWithShots(lastShot, "miss");
				}
			}

			// This will test to see if any ships have lives left to see if the ATTACKER won
			if (ships.get(0).isSunk() && ships.get(1).isSunk() && ships.get(2).isSunk() && ships.get(3).isSunk()
					&& ships.get(4).isSunk()) {
				sentences += "\nYour opponent has sunk your fleet";
			}

			// construct message
			result = new Feedback(sentences, "CreateAccount");

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
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

		// else if (arg0 instanceof ScoreboardData){}

	}

	protected void determinePlayer() {
		System.out.println("Not implemented");

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
