package Server;

//import packages 
import Controller.*;
import Utility.*;
import Utility.Error;
import Data.GameData;

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


    private JLabel status;
    private JTextArea serverMsg;
    private JTextField clientID;

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

    //player cred
    private String username;

    public String getUsername(){
        return this.username;
    }

    public GameClient(){

        super("localhost",8300);

    }

    public void setStatus(JLabel status){
        //Sets the private data field status to the input parameter status
        this.status = status;
    }

    public void setServerMsg(JTextArea serverMsg){
        //Sets the private data field serverMsg to the input parameter serverMsg
        this.serverMsg = serverMsg;

    }

    public void setClientID(JTextField clientID){
        //Sets the private data field clientID to the input parameter clientID
        this.clientID = clientID;
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
        //this method is called whenever the client's connection has been established with the server
        //update status

        //set the referenced jlabel status to "listening" and make text green
        // if(isConnected() == true){
        //     status.setText("Connected");
        //     status.setForeground(Color.GREEN);
        // }
        // else{
        //     status.setText("Waiting to connect...");
        //     status.setForeground(Color.CYAN);
        // }
        
        
    }

    public void handleMessageFromServer(Object arg0){
            
        // If we received a String, figure out what this event is.
        if (arg0 instanceof String){
            // Get the text of the message.
            String message = (String)arg0;
            
            // If we successfully logged in, tell the login controller.
            if (message.equals("LoginSuccessful"))
            {
                loginController.loginSuccess();

            }
            
            // If we successfully created an account, tell the create account controller.
            else if (message.equals("CreateAccountSuccessful"))
            {
                createAccountController.createAccountSuccess();
                //createAccountController.displayMessage(message);
            }
        }
        
        // If we received an Error, figure out where to display it.
        else if (arg0 instanceof Error){
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
            else if (error.getType().equals("MissingPlayer")){
                //bring back to Menu
                loginController.loginSuccess();
            }
        }

        // If received Feedback
        else if (arg0 instanceof Feedback){
            //Get the Feedback object
            Feedback feedback = (Feedback)arg0;

            // Player 1 is in a game room, OR player 2 joins player 1's game room
            if (feedback.getType().equals("CreatedGame") ||
                feedback.getType().equals("JoinedGame")){
                //implement
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
                startofGameControl.sendSOGdata(username);
            }        

            //login success
            else if (feedback.getType().equals("LoginSuccessfull")){
                loginController.loginSuccess();
                //assign the client's username
                this.username = feedback.getMessage();
            }
        }

        else if (arg0 instanceof ArrayList){

            ArrayList test = (ArrayList)arg0;

            //

        }

        //Server sends gameData
        else if (arg0 instanceof GameData){

            gameData = (GameData)arg0;

            // Initial turns...
            if (gameData.getType().equals("InitialPlayerTurn")){
                if (gameData.getTurn().equals("Your turn")){
                    gameControl.setStatus(gameData.getTurn());
                    gameControl.updateGrids(gameData,true,true);
                    startofGameControl.startGame(true,gameData.getTurn());
                }
                else if (gameData.getTurn().equals("Opponent turn")){
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

            else if (gameData.getType().equals("GameOver")){

                if (gameData.getFeedback().startsWith("You win!")){

                }
                else if (gameData.getFeedback().equals("You lost!")){

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
