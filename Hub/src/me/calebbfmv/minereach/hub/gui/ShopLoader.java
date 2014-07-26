package me.calebbfmv.minereach.hub.gui;

import java.util.ArrayList;
import java.util.List;

import me.calebbfmv.minereach.hub.Hub;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ShopLoader {

	public ShopLoader(){

	}

	public void loadShop(){
		FileConfiguration config = Hub.getInstance().getConfig();
		if(config == null){
			System.out.println("Null config");
			return;
		}
		ConfigurationSection section = config.getConfigurationSection("pages");
		if(section == null){
			System.out.println("Null section");
			return;
		}
		int i = 0;
		for(String s : section.getKeys(false)){
			List<ShopItem> items = new ArrayList<>();
			String path = s + ".items";
			ConfigurationSection sect = section.getConfigurationSection(path);
			for(String ss : sect.getKeys(false)){		
				i++;
				String typ = sect.getString(ss + ".type");
				Material type = Material.matchMaterial(typ);
				int price = sect.getInt(ss + ".price");
				int amount = sect.getInt(ss + ".amount", 1);
				List<String> commands = sect.getStringList(ss + ".commands");
				List<String> lore = sect.getStringList(ss + ".lore");
				boolean enchant = sect.getBoolean(ss + ".enchant");
				ShopItem item = new ShopItem(ss, price, commands, i, type, amount, lore, enchant);
				items.add(item);
			}
			new Page(section.getString(s + ".name"), items);
			items.clear();
		}
	}

}
