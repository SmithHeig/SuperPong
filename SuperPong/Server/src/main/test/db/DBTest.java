package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protocole.data.stats.Stats;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {
	static int count = 0;
	
	private static String url =  "jdbc:mysql://localhost:3306/SuperPong?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String DBusername = "root";
	private static String DBpassword = "root";
	private static Connection con;
	
	
	@BeforeEach
	void setUp() {
		System.out.println("Test DB numéro : " + ++count);
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("Fin du test numéro " + count);
	}
	
	@Test
	void getInstance() {
		DB instance = DB.getInstance();
		assertNotNull(instance);
		assertEquals(instance, DB.getInstance());
	}
	
	@Test
	void checkPlayer() {
		assertTrue(DB.getInstance().checkPlayer("james", "asdf")); // Check with good username and password
		assertFalse(DB.getInstance().checkPlayer("james", "test")); // With wrong password
		assertFalse(DB.getInstance().checkPlayer("test", "asdf")); // Wrong username
		assertFalse(DB.getInstance().checkPlayer("test", "wrong")); // Wrong username and password
	}
	
	@Test
	void addWin() {
		Stats stats = DB.getInstance().getStats("james");
		DB.getInstance().addWin("james");
		assertEquals(DB.getInstance().getStats("james").getNbWins(), stats.getNbWins() + 1);
		assertEquals(DB.getInstance().getStats("james").getNbPlays(), stats.getNbPlays() + 1);
	}
	
	@Test
	void addLoose() {
		Stats stats = DB.getInstance().getStats("james");
		DB.getInstance().addLoose("james");
		assertEquals(DB.getInstance().getStats("james").getNbWins(), stats.getNbWins());
		assertEquals(DB.getInstance().getStats("james").getNbPlays(), stats.getNbPlays() + 1);
	}
	
	@Test
	void changePassword() {
		assertTrue(DB.getInstance().checkPlayer("james", "asdf"));
		DB.getInstance().changePassword("james", "test");
		assertFalse(DB.getInstance().checkPlayer("james", "asdf"));
		assertTrue(DB.getInstance().checkPlayer("james", "test"));
		DB.getInstance().changePassword("james", "asdf");
		
	}
	
	@Test
	void isAdmin() {
		assertTrue(DB.getInstance().isAdmin("james"));
		assertFalse(DB.getInstance().isAdmin("eric"));
	}
	
	@Test
	void changeStats() {
		Stats stats = DB.getInstance().getStats("james");
		DB.getInstance().changeStats("james", 1000, 1000);
		Stats stats2 = DB.getInstance().getStats("james");
		assertEquals(stats2.getNbPlays(), 1000);
		assertEquals(stats2.getNbWins(), 1000);
		DB.getInstance().changeStats("james", stats.getNbWins(), stats.getNbPlays());
	}
}