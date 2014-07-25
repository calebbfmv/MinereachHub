package me.calebbfmv.minereach.hub;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class PlayerListener implements PluginMessageListener {

	public int total = 0;

	public PlayerListener(){
		Hub.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Hub.getInstance(), "BungeeCord");
		Hub.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Hub.getInstance(), "BungeeCord", this);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		System.out.println("Called");
		if (!channel.equals("BungeeCord")) {
			total = Bukkit.getOfflinePlayers().length;
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("PlayerCount")) {
			String server = in.readUTF();
			total = in.readInt();
			if(server.equalsIgnoreCase("ALL")){
				for(Player p : Bukkit.getOnlinePlayers()){
					player.sendMessage("u suk dock");
					player.sendMessage(total + "");
					Scoreboard board = Hub.getInstance().getBoard().getBoard(p, total);
					p.setScoreboard(board);
				}
			}
		}
	}

	public void sendRequest(){
		System.out.println(total);
		System.out.println("Sending");
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("PlayerCount");
			out.writeUTF("ALL");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("sent");
		first().sendPluginMessage(Hub.getInstance(), "BungeeCord", b.toByteArray());
	}

	private Player first(){
		return Bukkit.getOnlinePlayers()[0];
	}

}
