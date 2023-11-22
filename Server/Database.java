package Server;

// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Database {
    
    // private LoginData loginData;
    // private CreateAccountData createAccountData;
    
    // private File file;
    // private FileWriter fw;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rmd;
    // private String id;
    // private String username;
    // private String password;
    // private String query;
    // private String dml;

    // Constructor for DatabaseFile
    public Database() throws IOException { // REVISE: if needed...
        //Create a properties object
        Properties prop = new Properties();

        //Use a FileInputStream as input to the Properties object for reading the file
        //the db.properties file
        FileInputStream fis;
        try {
            fis = new FileInputStream("lab7out/db.properties");
            prop.load(fis);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Get the username,password, and url
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String pass = prop.getProperty("password");
        
        //Set the conn object
        try {
            conn = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // String currentDirectory = System.getProperty("user.dir");
        // file = new File(currentDirectory + "\\data.txt");
        // try {
        //     file.createNewFile();
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }

    // public void setQuery(String query){
    //     this.query = query;
    // }

    // public void setDML(String dml){
    //     this.dml= dml;
    // }

    public Boolean query(String query)
  {

    //instantiate arraylist
    ArrayList<String> records = new ArrayList<>();
    try {
      //Using the Conn object create a Statement object
      stmt=conn.createStatement();
      //Usinng the Statement object executeQuery using the input query (Returns the ResultSet)
      rs=stmt.executeQuery(query);
      
      // //Get metadata about the query
      rmd = rs.getMetaData();
      
      // //Get the # of columns
      int no_columns = rmd.getColumnCount();
    
    //   // //Get a column name
    //   String name="";

    //   for (int i = 0; i < no_columns; i++){
    //     name += rmd.getColumnName(i+1) + ",";
    //   }
    //   records.add(name);
      
      //Use a while loop to process the rows - Create a comma delimitted record from each field
      //and add comma delimited record to ArrayList
      while(rs.next()) //iterator
      {
        String row = "";
        for (int i = 0; i < no_columns; i++){
          row += rs.getString(i+1) + ",";
        }
        records.add(row);
        //record.add(rs.getString(2));
        // record.add(rs.getString(3));
        //System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
      }
      

    } catch (SQLException e) {
      // TODO: handle exception
      //e.printStackTrace();
      //return null since the query did not go through
      return false;
    }
    
    //return to show if it exists or not
    if (records.isEmpty()){
        return false;
    }
    else{
        return true;
    }
  }

    public String queryCheckPassword(String query)
  {
    String retrievedPW ="";
    try {      
      //Using the Conn object create a Statement object
      stmt=conn.createStatement();
      //Usinng the Statement object executeQuery using the input query (Returns the ResultSet)
      rs=stmt.executeQuery(query);
      
      while(rs.next()){
        retrievedPW = rs.getString(1);
      }
      
      

    } catch (SQLException e) {
      
      return "";
    }
    return retrievedPW;
  }
  
  public void executeDML(String dml) throws SQLException
  {
    Statement stmt;
    //1. Use the Conn object to create a Statement object
    stmt=conn.createStatement();
    //2. Run DML on the execute method of Statement
    stmt.execute(dml);
  }

    // public void setNewUserID(String id){
    //     this.id = id;

    // }

    // public void setNewUsername(String username){
    //     this.username = username;

    // }

    // public void setNewUserPassword(String password){
    //     this.password = password;
    // }



    // public void StoreData(){ //add data to database

    //     try {
    //         fw = new FileWriter(file.getName(),true);
    //         //fw.write("\n");
    //         fw.write(id + "," + username + "," + password + "\n");
    //         fw.close();
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         System.out.println("Something happened while writing username/password to file.");
    //         e.printStackTrace();
    //     } 
        
    // }

    // // public void StoreAfterAccountCreation(){

    // //     try {
    // //         fw = new FileWriter(file.getName(),true);
    // //         fw.write(id + "\n");
    // //         fw.close();
    // //     } catch (IOException e) {
    // //         // TODO Auto-generated catch block
    // //         System.out.println("Something happened while writing id to file.");
    // //         e.printStackTrace();
    // //     } 
    // // }

    // public Boolean RetrieveData(String UserCred){
    //     //do something with loginData to get the id, username, password
    //     Boolean result = false;
    //     try {
    //         Scanner scanner = new Scanner(file);
    //         while (scanner.hasNextLine()){ //this will never match unless it stops at a certain point
    //         String thisUser = scanner.nextLine();
    //         if (thisUser.equals(" ")) {
    //             break;
    //         }
    //         String[] values = thisUser.split(",");
    //         thisUser = values[1] + "," + values[2];
    //         if (thisUser.equals(UserCred)){
    //             result = true;
    //             break;
    //         }
            
    //         }
    //         scanner.close();
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         System.out.println("Something happened when reading from file.");
    //         System.out.println(e.getMessage());
    //     }
        
        
        
    //     return result;
    // }
}
