package Server;

//import packages 
import Controller.*;
import Utility.*;
import Utility.Error;
import Data.EndofGameData;
import Data.GameData;
import Data.ScoreboardData;

//

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient {

    //Controllers
    private LoginControl loginController;
    private CreateAccountControl createAccountController;
    private StartofGameControl startofGameControl;
    private GameControl gameControl;
    private EndofGameControl endofGameControl;
    private MenuControl menuControl;
    private ScoreboardControl scoreboardControl;

    //Data
    private GameData gameData;
    private EndofGameData endofGameData;

    //player cred
    private String username;

    public String getUsername(){
        return this.username;
    }

    public GameClient(){

        super("localhost",8300);

    }

    //controllers
    public void setLoginControl(LoginControl controller){
        loginController = controller;
    }

    public void setCreateAccountControl(CreateAccountControl controller){
        createAccountController = controller;
    }

    public void setStartofGameControl(StartofGameControl controller){
        startofGameControl = controller;
    }
    public void setGameControl(GameControl controller){
        gameControl = controller;
    }
    public void setEndofGameControl(EndofGameControl controller){
        endofGameControl = controller;
    }
    public void setMenuControl(MenuControl controller){
        menuControl = controller;
    }
    public void setScoreboardControl(ScoreboardControl controller){
        scoreboardControl = controller;
    }
    

    public void connectionEstablished(){ //hook
        System.out.println("Connected to server!");
    }

    public void handleMessageFromServer(Object arg0){
        
        // Error
        if (arg0 instanceof Error){
            // Get the Error object.
            Error error = (Error)arg0;
            
            // Display login errors using the login controller.
            if (error.getType().equals("Login")) {
                loginController.incorrectLogin(error.getMessage());
            }
            
            // Display account creation errors using the create account controller.
            else if (error.getType().equals("CreateAccount")) {
                createAccountController.invalidAccount(error.getMessage());
            }

            // If a player leaves before starting a game...
            else if (error.getType().equals("PlayerLeft")){
                //bring back to Menu
                loginController.loginSuccess();
                //set message
            }
        }

        // If received Feedback
        else if (arg0 instanceof Feedback){
            //Get the Feedback object
            Feedback feedback = (Feedback)arg0;

            // Player 1 is in a game room, OR player 2 joins player 1's game room
            if (feedback.getType().equals("CreatedGame") ||
                feedback.getType().equals("JoinedGame")){
                startofGameControl.setUsername(username);
            }
            // If player1 has been established
            else if (feedback.getType().equals("CreateGameWait")){
                //implement
                startofGameControl.setStatus(feedback.getMessage());
            }
            // Player 2 confirms ship placement, sends that they are now ready to play game
            else if (feedback.getType().equals("CreateGameReady")){
                //implement
                Feedback playGame = new Feedback("", "PlayGame");
                try {
                    sendToServer(playGame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Request player 1 / 2 to send back sogData
            else if (feedback.getType().equals("SendSOGData")){
                startofGameControl.sendSOGdata(this.username);
            }        

            //login success
            else if (feedback.getType().equals("LoginSuccessfull")){
                loginController.loginSuccess();
                //assign the client's username
                this.username = feedback.getMessage();
            }

            //create account success
            else if (feedback.getType().equals("CreateAccountSuccessful")){
                this.username = feedback.getMessage();
                createAccountController.createAccountSuccess();
            }

            // A player wins/loses
            else if (feedback.getType().equals("EndofGame")){

                //win
                if (feedback.getMessage().equals("You win!")){
                    endofGameData = new EndofGameData(true);
                    endofGameData.setPlayerUsername(username);
                    try {
                        sendToServer(endofGameData);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    gameControl.endGame(feedback.getMessage());
                }
                //loss
                else if (feedback.getMessage().equals("You lost!")){
                    endofGameData = new EndofGameData(false);
                    endofGameData.setPlayerUsername(username);
                    try {
                        sendToServer(endofGameData);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    gameControl.endGame(feedback.getMessage());
                }
            }
        }

        else if (arg0 instanceof ScoreboardData){
        	
        	ScoreboardData data = (ScoreboardData) arg0;
        	
            String[][] sbd = new String[6][3];
            for (int i = 0; i < data.getSb().size(); i++) {
            	String[] temp = data.getSb().get(i).split(" , ");
            	for (int j = 0; j < 3; j++) {
            		if (temp[j] == null) {
            			sbd[i][j] = "empty";
            		}
            		else {
            			sbd[i][j] = temp[j];
            	
            		}
            	}
            }
            
            scoreboardControl.buildLeaderboard(sbd);
            menuControl.showScreen();
        }

        //Server sends gameData
        else if (arg0 instanceof GameData){

            gameData = (GameData)arg0;

            // If both players have not joined
            if (gameData.getDetailedFeedback().startsWith("Waiting on opponent")){
                startofGameControl.setStatus(gameData.getDetailedFeedback());
                gameControl.setGameData(gameData);
            }

            // Initial turns...
            else if (gameData.getType().equals("InitialPlayerTurn")){
                if (gameData.getTurn().equals("Your turn")){
                    gameControl.setStatus("Opponent turn");
                    // gameControl.updateGrids(gameData,true,true);
                    gameControl.setGameData(gameData);
                    startofGameControl.startGame(true,gameData.getTurn());
                }
                else if (gameData.getTurn().equals("Opponent turn")){
                    // gameControl.setStatus("");
                    gameControl.setGameData(gameData);
                    startofGameControl.startGame(false,gameData.getTurn());
                }
                
            }

            else if (gameData.getType().equals("PlayerTurn")){

                if (gameData.getTurn().equals("Your turn")){
                    gameControl.setStatus(gameData.getTurn());
                  
                    gameControl.updateGrids(gameData,true,false);
                    // startofGameControl.startGame(true,gameData.getTurn());
                }
                else if (gameData.getTurn().equals("Opponent turn")){
                    // startofGameControl.startGame(false,gameData.getTurn());
                    gameControl.setStatus(gameData.getTurn());
                    
                    gameControl.updateGrids(gameData, false, true);
                }
            }            
        }
        
    }

    public void connectionException (Throwable exception) {
        System.out.println("Connection Exception Occurred");
        System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    
    
}
