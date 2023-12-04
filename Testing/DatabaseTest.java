package Testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import Server.Database;

//Testing database values:
/*
* User: pandawee
* Password: Muffin
* Wins: 1
* Losses: 1
*/

public class DatabaseTest {
	private Database database;
	private Connection conn;
	
	public DatabaseTest(){
		database = new Database();
		database.setConnection("../SWE-team2-Battleship/db.properties");
		conn = database.getConnection();
	}
	
	@Test
	public void testDatabase() {
		assertNotNull(conn);
	}

	@Test
	public void testQuery() {
		// Testing table has one entry only
		String query = "select username, aes_decrypt(password, 'key') from testingTable;";
		ArrayList<String> result = database.query(query);
		
		assertNotNull(result); //Tests if query returned null
		
	}

	@Test
	public void testGetConnection() {
		assertTrue(database.getConnection() instanceof Connection);

	}
	@Test
	public void testQueryCheckPassword() {
		String query = "select aes_decrypt(password, 'key') from testingTable;";
		String result = database.queryCheckPassword(query);
		String expected = "Muffin";
		assertEquals(result,expected);
	}

	@Test
	public void testSuccessfulExecuteDML() {
		String query = "update testingTable set wins=1 where username='pandawee';";
				
		try {
			database.executeDML(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("Exception occurred");
		}
	}
	
	@Test
	public void testFailedExecuteDML() {
		String query = "insert into testingTable values('rik',aes_encrypt('hello','key');";

      Exception exception = assertThrows(SQLException.class, () -> database.executeDML(query));	
  }
}
