package me.calebbfmv.minereach.hub.gui;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class GlowEnchant extends Enchantment {
	
	static boolean registered = false;
	public static final int ID = 112;
	public static final Enchantment instance = new GlowEnchant();

	public static boolean isRegistered() {
		return registered;
	}

	public static boolean appply(final ItemStack i) {
		if(i == null || registered == false) {
			return false;
		}
		i.addEnchantment(instance, 1);
		return true;
	}

	public GlowEnchant() {
		super(ID);
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		return "GLOW";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	public static void register() {
		try {
			final Enchantment e = Enchantment.getByName("GLOW");
			if(e != null && e.getClass() == GlowEnchant.class) {
				GlowEnchant.registered = true;
				return;
			}
			final Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(GlowEnchant.instance);
			GlowEnchant.registered = true;
			f.set(null, false);
		} catch (final Exception ex) {
			GlowEnchant.registered = false;
		}
	}
}
