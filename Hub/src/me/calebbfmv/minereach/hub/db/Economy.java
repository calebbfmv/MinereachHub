package me.calebbfmv.minereach.hub.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import me.calebbfmv.minereach.hub.Hub;
import me.calebbfmv.minereach.hub.events.PointsUpdateEvent;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Economy {

	public static MySQL sql = Hub.getInstance().getSql();

	public static void addCoins(final UUID player, final int amount){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				ResultSet res = sql.querySQL("SELECT " + Column.COIN_BALANCE.toString() + " FROM " + Column.CURRENCY_TABLE.toString() + " WHERE " + Column.KEY_PLAYER.toString() + "='" + player.toString() + "'");
				try {
					if(res.next()){
						int balance = res.getInt(Column.COIN_BALANCE.formatted()) + amount;
						sql.updateSQL("UPDATE " + Column.CURRENCY_TABLE.toString() + " SET" + Column.COIN_BALANCE.toString() + "= " + balance + " WHERE " + Column.KEY_PLAYER.toString() + "='" + player.toString() + "'");
						res.close();
						PointsUpdateEvent event = new PointsUpdateEvent(Bukkit.getPlayer(player), balance);
						Bukkit.getPluginManager().callEvent(event);
					} else {
						res.close();
						sql.updateSQL("INSERT INTO " + Column.CURRENCY_TABLE.toString() + " VALUES('" + player.toString() + "', " + amount + ")");
						PointsUpdateEvent event = new PointsUpdateEvent(Bukkit.getPlayer(player), amount);
						Bukkit.getPluginManager().callEvent(event);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sql.closeConnection();
			}
		});
		thread.start();
		thread.setName("TR-AddCoins");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void removeCoins(final UUID player, final int amount){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				ResultSet res = sql.querySQL("SELECT " + Column.COIN_BALANCE.toString() + " FROM " + Column.CURRENCY_TABLE.toString() + " WHERE " + Column.KEY_PLAYER.toString() + "='" + player.toString() + "'");
				try {
					if(res.next()){
						int balance = res.getInt(Column.COIN_BALANCE.formatted()) - amount;
						sql.updateSQL("UPDATE " + Column.CURRENCY_TABLE.toString() + " SET" + Column.COIN_BALANCE.toString() + "= " + balance + " WHERE " + Column.KEY_PLAYER.toString() + "='" + player.toString() + "'");
						res.close();
						PointsUpdateEvent event = new PointsUpdateEvent(Bukkit.getPlayer(player), balance);
						Bukkit.getPluginManager().callEvent(event);
					} else {
						res.close();
						sql.updateSQL("INSERT INTO " + Column.CURRENCY_TABLE.toString() + " VALUES('" + player.toString() + "', " + amount + ")");
						PointsUpdateEvent event = new PointsUpdateEvent(Bukkit.getPlayer(player), amount);
						Bukkit.getPluginManager().callEvent(event);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sql.closeConnection();
			}
		});
		thread.start();
		thread.setName("TR-RemoveCoins");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int getCoins(final UUID player){
		final HashMap<String, Integer> coins = new HashMap<>();
		coins.put(player.toString(), 0);
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				ResultSet res = sql.querySQL("SELECT " + Column.COIN_BALANCE.toString() + " FROM " + Column.CURRENCY_TABLE.toString() + " WHERE " + Column.KEY_PLAYER.toString() + "='" + player.toString() + "'");
				try {
					if(res.next()){
						coins.put(player.toString(), res.getInt(Column.COIN_BALANCE.formatted()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread.setName("TR-GetCoins:" + player.toString());
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return coins.get(player.toString());
	}
	
	public static void addOffline(OfflinePlayer player, int amount){
		addCoins(player.getUniqueId(), amount);
	}
	
	public static void removeOffline(OfflinePlayer player, int amount){
		removeCoins(player.getUniqueId(), amount);
	}
	
	public static int getCoinsOffline(OfflinePlayer player){
		return getCoins(player.getUniqueId());
	}
}
