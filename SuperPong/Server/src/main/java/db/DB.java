package db;

import protocole.data.stats.Stats;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(DB.class.getName());

    /* INSTANCE */
    private final static DB instance = new DB();

    /* ATTRIBUTS */
    // TODO: METTRE DANS un fichier de config
    private static final String url = "jdbc:mysql://localhost:3306/SuperPong?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DBusername = "root";
    private static final String DBpassword ="root";
    private Connection con;

    private DB(){
        connection();
    }

    public static DB getInstance(){
        return instance;
    }

    private void connection(){
        try{
            con = DriverManager.getConnection(url,DBusername,DBpassword);
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error with connection to sql server with excpetion: " + e.getMessage());
        }
    }

    // TODO Géré un username inconnu
    public boolean checkPlayer(String username, String password){
        try{
            PreparedStatement statement = con.prepareStatement("SELECT password FROM SuperPong.User WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            /* Doit en avoir un seul vu que les username sont unique */
            String UserPassword = "";

            /* Doit en avoir que un car username unique */
            while(resultSet.next()){
                 UserPassword = resultSet.getString("password");
            }
            if(UserPassword.equals(password)){
                return true;
            }
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error getting password from user with excpetion : " + e.getMessage());
        }
        return false;
    }

    public void addWin(String username){
        try{
            LOG.log(Level.INFO, "Add a win to the DB for " + username);
            PreparedStatement statement = con.prepareStatement("UPDATE User SET nbWins = nbWins + 1, nbPlays = nbPlays + 1 WHERE username=?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error getting password from user with excpetion : " + e.getMessage());
        }
    }

    public void addLoose(String username){
        try{
            LOG.log(Level.INFO, "Add a loose to the DB for " + username);
            PreparedStatement statement = con.prepareStatement("UPDATE User SET nbPlays = nbPlays + 1 WHERE username=?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error getting password from user with excpetion : " + e.getMessage());
        }
    }

    public Stats getStats(String username){
        try{
            LOG.log(Level.INFO, "Getting the stats of " + username);
            PreparedStatement statement = con.prepareStatement("SELECT nbWins, nbPlays FROM User WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            /* DOit avoir un seul résultat car username unique */
            String nbWins = "";
            String nbPlays = "";

            while(resultSet.next()){
                nbWins = resultSet.getString("nbWins");
                nbPlays = resultSet.getString("nbPlays");
            }

            Stats stats = new Stats(Integer.parseInt(nbWins), Integer.parseInt(nbPlays));
            return stats;

        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error getting password from user with excpetion : " + e.getMessage());
        }
        return null;
    }

}
