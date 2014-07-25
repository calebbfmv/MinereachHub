package me.calebbfmv.minereach.hub.db;

import java.sql.Connection;

import org.bukkit.plugin.Plugin;

/**
 * TBH this should just require a possible logger instance instead of storing the plugin
 * It would have a lot more versatility than requiring a plugin instance
 * On top of that, it's not as if there are multiple different classes extending this
 *
 * someone do something, plez
 *
 * Abstract Database class, serves as a base for any connection method (MySQL, SQLite, etc.)
 * 
 * @author -_Husky_-
 * @author tips48
 */
public abstract class Database {

    /**
     * Plugin instance, use for plugin.getDataFolder() and plugin.getLogger()
     */
    protected final Plugin plugin;

    /**
     * Creates a new Database
     * 
     * @param plugin Plugin instance
     */
    protected Database(Plugin plugin) {
        this.plugin = plugin;
    }

	/**
	 * Get the plugin instance creating this Database
	 *
	 * @return Connection opened
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * Opens a connection with the database
	 *
	 * @return Connection opened
	 */
	public abstract Connection openConnection();

    /**
     * Checks if a connection is open with the database
     * 
     * @return true if a connection is open
     */
    public abstract boolean checkConnection();

    /**
     * Gets the connection with the database
     * 
     * @return Connection with the database, null if none
     */
    public abstract Connection getConnection();

    /**
     * Closes the connection with the database
     */
    public abstract void closeConnection();
}