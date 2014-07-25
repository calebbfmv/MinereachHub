package me.calebbfmv.minereach.hub;

import me.calebbfmv.minereach.hub.commands.PointsCommand;
import me.calebbfmv.minereach.hub.db.Column;
import me.calebbfmv.minereach.hub.db.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Hub extends JavaPlugin {
	
	private static Hub instance;
	private MySQL sql;
	private Sidebar bar;
	private PlayerListener playerListener;
	private String host, db, pass, user, port;
	
	public static Hub getInstance(){
		return instance;
	}
	
	public void onEnable(){
		instance = this;
		new WeatherListener();
		new Announcements();
		bar = new Sidebar();
		new PointsCommand();
		playerListener = new PlayerListener();
		host = getConfig().getString("db.host");
		db = getConfig().getString("db.database-name");
		pass = getConfig().getString("db.password");
		user = getConfig().getString("db.user");
		port = getConfig().getString("db.port");
		connect();
		for(World world : Bukkit.getWorlds()){
			world.setStorm(false);
		}
		for(Player player :  Bukkit.getOnlinePlayers()){
			player.sendMessage("Luke is gay");
		}
		update();
	}

	private void connect(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				sql = new MySQL(getInstance(), host, port, db, user, pass);
				sql.updateSQL("CREATE TABLE IF NOT EXISTS " + Column.CURRENCY_TABLE.toString() + "(" + Column.KEY_PLAYER.toString() + " varchar(64), " + Column.COIN_BALANCE.toString() + " float, PRIMARY KEY(" + Column.KEY_PLAYER.toString() + "))");
				sql.closeConnection();
			}
		});
		thread.start();
		thread.setName("MinereachDB");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public MySQL getSql() {
		return sql;
	}
	
	public Sidebar getBoard(){
		return bar;
	}

	public PlayerListener getPlayerListener() {
		return playerListener;
	}

	public String getHost() {
		return host;
	}

	public String getDb() {
		return db;
	}

	public String getPass() {
		return pass;
	}

	public String getUser() {
		return user;
	}

	public String getPort() {
		return port;
	}
	
	private void update(){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(getBoard().getBoard(player, playerListener.total));
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				System.out.println("Updating");
				playerListener.sendRequest();
			}
		}.runTaskTimerAsynchronously(getInstance(), 0L, 20L);
	}

}
