import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import Server.GameServer;

public class ServerGUI extends JFrame {
    
    private GameServer server;
    private JLabel status; //Initialized to “Not Connected”
    private JTextField textFieldPort;
    private JTextField textFieldTimeout;

    private JTextArea log;

    private JButton buttonListen;
    private JButton buttonClose;
    private JButton buttonStop;
    private JButton buttonQuit;
    private Boolean initialStartup = true;


    public ServerGUI(String title){

        //instantiate the ChatServer server
        server = new GameServer();  //Step A
        //start database
        server.startDatabase();
        //title
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //System.out.println("Hello world!");
        //For upper portion
        JPanel top = new JPanel(new GridLayout(3,1,0,10));

        //Create "Not Connected" button, with red font color
        status = new JLabel();
        status.setText("Not Connected");
        status.setForeground(Color.RED);

        JLabel status_check = new JLabel("Status:");

        //Store this status and status_check into FL JPanel
        JPanel north = new JPanel(); //FL
        north.add(status_check);
        north.add(status);

        top.add(north);

        // Create port # and timeout fields and jtextfields
        JPanel panel1 = new JPanel(); //FL
        JLabel label1 = new JLabel("Port #");
        textFieldPort = new JTextField(6);
        //hardcode settings for these
        textFieldPort.setText("8300");
        panel1.add(label1);
        panel1.add(textFieldPort);
        top.add(panel1);
        JPanel panel2 = new JPanel(); //FL
        JLabel label2 = new JLabel("Timeout");
        textFieldTimeout = new JTextField(7);
        textFieldTimeout.setText("500");
        panel2.add(label2);
        panel2.add(textFieldTimeout);
        top.add(panel2);
       

        //For lower portion
        JPanel bottom = new JPanel(new BorderLayout());

        JLabel server_log_below = new JLabel("Server Log Below");
        JPanel server_log_panel = new JPanel(); //FL
        //bottom.add(server_log_below, BorderLayout.NORTH);
        //add server_log_below to FL JPanel to center it
        server_log_panel.add(server_log_below);
        bottom.add(server_log_panel, BorderLayout.NORTH);

        log = new JTextArea(10,40);
        JScrollPane sp = new JScrollPane(log);
        bottom.add(sp, BorderLayout.CENTER);

        //for bottom portion of bottom JPanel
        JPanel buttonPanel = new JPanel(); //FL

        //buttons
        buttonListen = new JButton("Listen");
        buttonClose = new JButton("Close");
        buttonStop = new JButton("Stop");
        buttonQuit = new JButton("Quit");

        //Add ActionListener for each button
        EventHandler eventHandler = new EventHandler();
        buttonListen.addActionListener(eventHandler);
        buttonClose.addActionListener(eventHandler);
        buttonStop.addActionListener(eventHandler);
        buttonQuit.addActionListener(eventHandler);
        
        //add to panel
        buttonPanel.add(buttonListen);
        buttonPanel.add(buttonClose);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonQuit);

        //add to bottom BL
        bottom.add(buttonPanel, BorderLayout.SOUTH);
        
        

        //Buffer panels to FL
        JPanel upperPanel = new JPanel(); //FL
        JPanel bottomPanel = new JPanel(); //FL
        
        upperPanel.add(top);
        bottomPanel.add(bottom);

        this.add(upperPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);

        //Create window and size
        this.setSize(500, 500);
        this.setVisible(true);

        //give the reference to the status JLabel and JTextArea log to the server
        server.setStatus(status);
        server.setLog(log);
        
        if (initialStartup){
            buttonListen.doClick();
        }
        initialStartup = false;
    }

    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            //Events for buttons

            //If the port#/timeout are not set to integers, then print error message
            if (event.getSource() == buttonListen) {
                if (server.isListening()){
                    log.append("Server already listening.\n");
                }
                else {
                    try {
                        int port = Integer.parseInt(textFieldPort.getText());
                        int timeout = Integer.parseInt(textFieldTimeout.getText());
                        server.setPort(port); //Step B
                        server.setTimeout(timeout); // Step C
                        server.listen();//ServerStarted Step D
                    } catch (Exception e) {
                        // TODO: handle exception
                        log.append("Port Number/timeout not entered before pressing Listen. Issue: " + e.getMessage() + "\n");
                        e.printStackTrace();
                    }
                }                
            }
            // same as before, but use close method. This method will invoke both serverClosed() and serverStopped()
            else if (event.getSource() == buttonClose){
                if(server.isListening()){
                    //try to close
                    try {
                        server.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    //update server log 
                    log.append("Server not currently started" + "\n");
                }
            }

            //if the user presses the Stop button before the server is started, do nothing. 
            //if the user presses the Stop button while connected to server, then use server.stopListening() method
            // to stop the server from listening. The status label will also be switched to red "Stopped".
            // Then to provide feedback to use the listen button again to start listening
            else if (event.getSource() == buttonStop){
                //if server is listening
                if (server.isListening()){ 
                    
                    server.stopListening();
                }
                else{ //if not listening
                    //update server log 
                    log.append("Server not currently started" + "\n");
                }
            }

            else if (event.getSource() == buttonQuit){
                //close the GUI
                setVisible(false); //set to invisible
                dispose(); //Destroy the JFrame object
            }
                
    
            
        }
    }

    public static void main(String[] args)
    {
        //new ServerGUI(args[0]); //args[0] represents the title of the GUI
        new ServerGUI("Server");
    }
}
