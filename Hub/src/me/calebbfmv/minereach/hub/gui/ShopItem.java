package me.calebbfmv.minereach.hub.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem {

	private String name;
	private int price;
	private List<String> commands;
	private int slot, amount;
	private Material type;
	private List<String> lore;
	private boolean enchant;
	private static HashMap<Integer, ShopItem> items = new HashMap<>();

	/**
	 * Create a new ShopItem for players to buy.
	 * @param name The name of the item to display (ChatColors supported)
	 * @param price The price of this item, displayed on the lore.
	 * @param command The command to be exectued when bought.
	 * @param slot The slot in the GUI. Used for referencing on InventoryClickEvent
	 * @param type The material type for the item to be
	 * @param amount The amount of the item to give
	 * @param lore The lore of the item, colors will be added
	 * @param enchant A boolean for adding a "glow" effect to the item.
	 */
	public ShopItem(String name, int price, List<String> commands, int slot, Material type, int amount, List<String> lore, boolean enchant){
		this.name = name;
		this.commands = commands;
		this.price = price;
		this.slot = slot;
		this.type = type;
		this.lore = lore;
		this.enchant = enchant;
		items.put(slot, this);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Material getType() {
		return type;
	}

	public List<String> getLore() {
		return lore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommand(List<String> command) {
		this.commands = command;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public static HashMap<Integer, ShopItem> getItems() {
		return items;
	}

	public ItemStack toItemStack(){
		List<String> ll = new ArrayList<>();
		for(int i = 0; i < lore.size(); i++){
			String s  = lore.get(i);
			s = ChatColor.WHITE + s;
			s = ChatColor.translateAlternateColorCodes('&', s);
			s = s.replace("-", " ");
			ll.add(s);
		}
		ItemStack stack = new ItemStack(type);
		ItemMeta meta = stack.getItemMeta();
		name = ChatColor.translateAlternateColorCodes('&', name);
		meta.setDisplayName(name);
		meta.setLore(ll);
		stack.setItemMeta(meta);
		stack.setAmount(amount);
		if(enchant)
			GlowEnchant.appply(stack);
		return stack;
	}

	public static ShopItem getFromSlot(int slot){
		return items.get(slot);
	}

}
