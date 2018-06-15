package db;

import protocole.data.stats.Stats;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
	/* LOGGER */
	private final static Logger LOG = Logger.getLogger(DB.class.getName());
	
	/* INSTANCE */
	private final static DB instance = new DB();
	
	private String url;
	private String DBusername;
	private String DBpassword;
	private Connection con;
	
	private DB() {
		try {
			Properties properties = new Properties();
			/* ATTRIBUTS */
			InputStream in = getClass().getClassLoader().getResourceAsStream("config/configServer.properties");
			properties.load(in);
			url = "jdbc:mysql://localhost:3306/" + properties.getProperty("DBName") + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
			DBusername = properties.getProperty("DBusername");
			DBpassword = properties.getProperty("DBpassword");
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection();
	}
	
	public static DB getInstance() {
		return instance;
	}
	
	private void connection() {
		try {
			con = DriverManager.getConnection(url, DBusername, DBpassword);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error with connection to sql server with exception: " + e.getMessage());
		}
	}
	
	public boolean checkPlayer(String username, String password) {
		try {
			PreparedStatement statement = con.prepareStatement("SELECT `password` FROM `SuperPong`.`User` WHERE `username`=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			/* Doit en avoir un seul vu que les username sont unique */
			String UserPassword = "";
			
			/* Doit en avoir que un car username unique */
			while (resultSet.next()) {
				UserPassword = resultSet.getString("password");
			}
			if (UserPassword.equals(password)) {
				return true;
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting password from user with exception : " + e.getMessage());
		}
		return false;
	}
	
	public void addWin(String username) {
		try {
			LOG.log(Level.INFO, "Add a win to the DB for " + username);
			PreparedStatement statement = con.prepareStatement("UPDATE `User` SET `nbWins` = `nbWins` + 1, `nbPlays` = `nbPlays` + 1 WHERE `username`=?");
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting password from user with exception : " + e.getMessage());
		}
	}
	
	public void addLoose(String username) {
		try {
			LOG.log(Level.INFO, "Add a loose to the DB for " + username);
			PreparedStatement statement = con.prepareStatement("UPDATE `User` SET `nbPlays` = `nbPlays` + 1 WHERE `username`=?");
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting password from user with exception : " + e.getMessage());
		}
	}
	
	public Stats getStats(String username) {
		try {
			LOG.log(Level.INFO, "Getting the stats of " + username);
			PreparedStatement statement = con.prepareStatement("SELECT `nbWins`, `nbPlays` FROM `User` WHERE `username`=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			
			/* DOit avoir un seul résultat car username unique */
			String nbWins = "";
			String nbPlays = "";
			
			while (resultSet.next()) {
				nbWins = resultSet.getString("nbWins");
				nbPlays = resultSet.getString("nbPlays");
			}
			
			return new Stats(Integer.parseInt(nbWins), Integer.parseInt(nbPlays));
			
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting password from user with exception : " + e.getMessage());
		}
		return null;
	}
	
	public boolean changePassword(String username, String password) {
		try {
			LOG.log(Level.INFO, "Trying to change password for " + username);
			PreparedStatement statement = con.prepareStatement("UPDATE `User` SET `password`=? WHERE `username`=?");
			statement.setString(1, password);
			statement.setString(2, username);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error changing password with exception : " + e.getMessage());
			return false;
		}
	}
	
	public boolean isAdmin(String username) {
		try {
			LOG.log(Level.INFO, "Is the user an admin: " + username);
			PreparedStatement statement = con.prepareStatement("SELECT `isAdmin` FROM `User` WHERE `username`=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			
			/* DOit avoir un seul résultat car username unique */
			boolean isAdmin = false;
			
			while (resultSet.next()) {
				isAdmin = resultSet.getBoolean("isAdmin");
			}
			
			return isAdmin;
			
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "username incorrect ! exception: " + e.getMessage());
			return false;
		}
	}
	
	public boolean changeStats(String username, int nbWins, int nbPlays) {
		try {
			LOG.log(Level.INFO, "Try to change stats of user " + username);
			PreparedStatement statement = con.prepareStatement("UPDATE `User` SET `nbWins`=?, `nbPlays`=? WHERE `username`=?");
			statement.setInt(1, nbWins);
			statement.setInt(2, nbPlays);
			statement.setString(3, username);
			int nbChanged = statement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Impossible to edit stats with exception: " + e.getMessage());
			return false;
		}
	}
	
}
