package me.calebbfmv.minereach.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PointsUpdateEvent extends Event {
	
	public static final HandlerList list = new HandlerList();
	
	private Player player;
	private int amount;
	
	public PointsUpdateEvent(Player player, int amount){
		this.amount = amount;
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getAmount(){
		return amount;
	}

	@Override
	public HandlerList getHandlers() {
		return list;
	}
	
	public static HandlerList getHandlerList(){
		return list;
	}

}
