package me.calebbfmv.minereach.hub.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Page {

	private int id;
	private Inventory inv;
	private String name;
	int i = 0;
	private List<ShopItem> items;
	private static HashMap<Integer, Page> pages = new HashMap<>();
	private static HashMap<String, Page> pageNames = new HashMap<>();

	public Page(String name, List<ShopItem> items){
		this.name = name;
		this.items = items;
		if(!pages.keySet().isEmpty()){
			i = Collections.max(pages.keySet());
			this.id = i + 1;
		} else {
			this.id = 0;
		}
		pages.put(id, this);
		name = ChatColor.GOLD + name;
		pageNames.put(name, this);
		i = items.size();
		i = (i + 8) / 9 * 9;
		this.inv = Bukkit.createInventory(null, i, name);
		int s = 1;
		for(ShopItem item : items){
			if(s < inv.getSize() - 1){
				inv.setItem(s, item.toItemStack());
				item.setSlot(s);
				s++;
			}
		}
		ItemStack next = new ItemStack(Material.GOLD_INGOT);
		ItemStack back = new ItemStack(Material.REDSTONE);
		ItemMeta nMeta = next.getItemMeta();
		ItemMeta bMeta = back.getItemMeta();
		nMeta.setDisplayName(ChatColor.GREEN + ChatColor.BOLD.toString() + ChatColor.ITALIC + "Next");
		next.setItemMeta(nMeta);
		bMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + ChatColor.ITALIC + "Back");
		back.setItemMeta(bMeta);
		inv.setItem(0, back);
		inv.setItem(inv.getSize() - 1, next);
	}

	public int getId() {
		return id;
	}

	public Inventory getInv() {
		return inv;
	}

	public String getName() {
		return name;
	}

	public List<ShopItem> getItems() {
		return items;
	}

	public Page getPrev(){
		return pages.get(id -1);
	}

	public Page getNext(){
		return pages.get(id + 1);
	}

	public static Page getPage(int id) {
		return pages.get(id);
	}

	public static Inventory first(){
		return pages.get(0).getInv();
	}

	public static Page getFromName(String name){
		return pageNames.get(name);
	}

}
