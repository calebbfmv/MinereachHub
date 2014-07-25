package me.calebbfmv.minereach.hub.commands;

import me.calebbfmv.minereach.hub.Hub;
import me.calebbfmv.minereach.hub.db.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointsCommand implements CommandExecutor {
	
	private Hub hub;
	public PointsCommand(){
		hub = Hub.getInstance();
		hub.getCommand("points").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length != 3){
			sender.sendMessage("Usage: /points <add/remove> <player> <amount>");
			return true;
		}
		if(!sender.hasPermission("hub.points.manage") && !sender.isOp()){
			sender.sendMessage(ChatColor.RED + "You cannot use this command!");
			return true;
		}
		String option = args[0];
		switch(option){
		case "add":
			add(sender, args);
			break;
		case "remove":
			break;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void add(CommandSender sender, String[] args){
		String player = args[1];
		String amt = args[2];
		int amount = -1;
		try {
			amount = Integer.parseInt(amt);
		} catch (NumberFormatException e){
			sender.sendMessage(ChatColor.GOLD + amt + ChatColor.RED + " is not a valid number!"); 
			return;
		}
		Player p = Bukkit.getPlayerExact(player);
		if(player == null){
			OfflinePlayer offline = Bukkit.getOfflinePlayer(player);
			Economy.addOffline(offline, amount);
			return;
		}
		Economy.addCoins(p.getUniqueId(), amount);
		
	}

}
