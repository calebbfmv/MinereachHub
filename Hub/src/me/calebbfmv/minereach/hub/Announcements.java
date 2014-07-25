package me.calebbfmv.minereach.hub;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Announcements extends BukkitRunnable {

	private Hub hub;
	private List<String> messages =  new ArrayList<>();
	private int last = 0;
	private int timeBetween;

	public Announcements(){
		hub = Hub.getInstance();
		hub.saveDefaultConfig();
		List<String> temo = hub.getConfig().getStringList("announcements");
		for(String s : temo){
			s = ChatColor.translateAlternateColorCodes('&', s);
			messages.add(s);
		}
		temo.clear();
		timeBetween = hub.getConfig().getInt("announcement-interval", 10);
		this.runTaskTimer(hub, 20L * timeBetween, 20L* timeBetween);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class)
				for(Player player : ((Collection<Player>)Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]))){
					String message = "";
					try {
						message = messages.get(last);
					} catch (ArrayIndexOutOfBoundsException e){
						last = 0;
						message = messages.get(0);
					}
					message = message.replace("%player%", player.getName());
					player.sendMessage(message);
				}
			else
				for(Player player : ((Player[])Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]))){
					String message = "";
					try {
						message = messages.get(last);
					} catch (ArrayIndexOutOfBoundsException e){
						last = 0;
						message = messages.get(0);
					}
					message = message.replace("%player%", player.getName());
					player.sendMessage(message);
				}
			if((last +1) >= messages.size()){
				last = 0;
			} else {
				last++;
			}
		}
		catch (NoSuchMethodException ex){} // can never happen
		catch (InvocationTargetException ex){} // can also never happen
		catch (IllegalAccessException ex){} // can still never happen
	}

}
