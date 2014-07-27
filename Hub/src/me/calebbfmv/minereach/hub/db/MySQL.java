package me.calebbfmv.minereach.hub.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

public class MySQL extends Database {
    
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;


    /**
     * Creates a new MySQL instance
     * 
     * @param plugin Plugin instance
     * @param hostname Name of the host
     * @param port Port number
     * @param database Database name
     * @param username Username
     * @param password Password
     */
    public MySQL(Plugin plugin, String hostname, String port, String database, String username, String password) {
        super(plugin);
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }


    @Override
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String statement = "CREATE DATABASE IF NOT EXISTS " + database;
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port, user, password);
            Statement st = conn.createStatement();
            st.executeUpdate(statement);
            st.close();
            conn.close();
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException exception) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + exception.getMessage());
        } catch (ClassNotFoundException exception) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
    }


    @Override
    public boolean checkConnection() {
        return connection != null;
    }


    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!", exception);
            }
        }
    }


    public ResultSet querySQL(String query) {
        Connection c = openConnection();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
	    
        ResultSet ret = null;
	    
        try {
            ret = s.executeQuery(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ret;
    }


    public void updateSQL(String update) {
        Connection c = openConnection();
        try {
	        PreparedStatement s = c.prepareStatement(update);
            s.executeUpdate(update);
        } catch (SQLException exception) {
	        plugin.getLogger().log(Level.SEVERE, "Error updating SQL!", exception);
        }
    }
}
