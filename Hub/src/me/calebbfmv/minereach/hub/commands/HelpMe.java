package me.calebbfmv.minereach.hub.commands;

import me.calebbfmv.minereach.hub.Hub;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpMe implements CommandExecutor {
	
	private Hub hub;
	private String[] help;
	public HelpMe(){
		hub = Hub.getInstance();
		hub.getCommand("helpme").setExecutor(this);
		help = new String[hub.getConfig().getStringList("helpme").size()];
		for(int i = 0; i < help.length; i++){
			String s = hub.getConfig().getStringList("helpme").get(i);
			s = ChatColor.translateAlternateColorCodes('&', s);
			help[i] = s;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(help);
		return false;
		
	}

}
