package me.calebbfmv.minereach.hub;

import me.calebbfmv.minereach.hub.db.Economy;
import me.calebbfmv.minereach.hub.events.PointsUpdateEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Sidebar implements Listener {

	private Hub hub;

	public Sidebar() {
		hub = Hub.getInstance();
		hub.getServer().getPluginManager().registerEvents(this, hub);
	}

	public Scoreboard getBoard(Player player, int playersOnline) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("sidebar", "dummy");
		String displayName = hub.getConfig().getString("sidebar.name");
		displayName = ChatColor.translateAlternateColorCodes('&', displayName);
		if (displayName.length() > 32) {
			displayName = displayName.substring(0, 32);
		}
		obj.setDisplayName(displayName);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		space(obj);
		Score playerString = obj.getScore(ChatColor.GOLD + "Players");
		Score players = obj.getScore(playersOnline + " / 300");
		playerString.setScore(14);
		players.setScore(13);
		Score websiteString = obj.getScore(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Website");
		Score website = obj.getScore("minereach.com");
		websiteString.setScore(11);
		website.setScore(10);
		Score pointString = obj.getScore(ChatColor.RED + ChatColor.BOLD.toString() + "Points");
		int i = Economy.getCoins(player.getUniqueId());
		String pp = String.valueOf(i);
		if (i >= 1000) {
			pp = pp.substring(0, 1) + "," + pp.substring(1);
		}
		if (i >= 10000) {
			pp = String.valueOf(i);
			pp = pp.substring(0, 2) + "," + pp.substring(2);
		}
		if (i >= 100000) {
			pp = String.valueOf(i);
			pp = pp.substring(0, 3) + "," + pp.substring(3);
		}
		if (i >= 1000000) {
			pp = "999,999";
			i = 999999;
		}
		Score points = obj.getScore(pp);
		pointString.setScore(8);
		points.setScore(7);
		Score helpString = obj.getScore(ChatColor.AQUA + "Need Help?");
		Score help = obj.getScore("/helpme");
		helpString.setScore(5);
		help.setScore(4);
		return board;
	}

	private void space(Objective main) {
		Score space = main.getScore("");
		space.setScore(15);
		String spaceEntry = space.getEntry();
		spaceEntry += " ";
		space = main.getScore(spaceEntry);
		space.setScore(12);
		spaceEntry += " ";
		space = main.getScore(spaceEntry);
		space.setScore(9);
		spaceEntry += " ";
		space = main.getScore(spaceEntry);
		space.setScore(6);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		PlayerListener listener = Hub.getInstance().getPlayerListener();
		listener.sendRequest();
		event.getPlayer().setScoreboard(getBoard(event.getPlayer(), listener.total));
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(getBoard(player, listener.total));
		}
	}

	@EventHandler
	public void onUpdate(PointsUpdateEvent event) {
		PlayerListener listener = Hub.getInstance().getPlayerListener();
		listener.sendRequest();
		event.getPlayer().setScoreboard(getBoard(event.getPlayer(), listener.total));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		PlayerListener listener = Hub.getInstance().getPlayerListener();
		listener.sendRequest();
		for(Player player : Bukkit.getOnlinePlayers()){
			player.setScoreboard(getBoard(player, listener.total));
		}
	}
}
