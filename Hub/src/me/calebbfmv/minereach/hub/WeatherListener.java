package me.calebbfmv.minereach.hub;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {
	
	public WeatherListener(){
		Hub hub = Hub.getInstance();
		hub.getServer().getPluginManager().registerEvents(this, hub);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent event){
		event.setCancelled(true);
	}

}
