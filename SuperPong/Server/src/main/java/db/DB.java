package db;

import java.io.IOException;
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
    private static final String url = "jdbc:mysql://localhost:3306/SuperPong";
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
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,DBusername,DBpassword);
        } catch (SQLException | ClassNotFoundException e){
            LOG.log(Level.SEVERE, "Error with connection to sql server with excpetion: " + e.getMessage());
        }
    }

    public boolean checkPlayer(String username, String password){
        try{
            PreparedStatement statement = con.prepareStatement("SELECT password FROM SuperPong.User WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            System.out.println(resultSet);
        } catch (SQLException e){
            LOG.log(Level.SEVERE, "Error getting password from user with excpetion : " + e.getMessage());
        }
        return true; // TODO à changer
    }

}
