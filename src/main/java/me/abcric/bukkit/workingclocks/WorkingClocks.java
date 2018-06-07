package me.abcric.bukkit.workingclocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WorkingClocks extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getLogger().info("Enabling...");
		getServer().getPluginManager().registerEvents(this, this);
		
		// load config
		getLogger().info("Loading config...");
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getLogger().info("Enabled.");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (isHoldingClock(e.getPlayer()) && e.getPlayer().hasPermission("WorkingClocks.use")) {
			long time = e.getPlayer().getWorld().getTime();
			String message = ChatColor.YELLOW + "You look at your clock... It's "
			               + (getConfig().getBoolean("clock-12h", false) ? ticksTo12h(time) : ticksTo24h(time))
			               + ".";
			e.getPlayer().sendMessage(message);
		}
	}

	private boolean isHoldingClock(Player p) {
		return p.getInventory().getItemInMainHand().getType() == Material.WATCH
		    || p.getInventory().getItemInOffHand().getType() == Material.WATCH;
	}
	
	public static String ticksTo24h(long ticks) {
		// tick 0 == tick 24000 == 6:00
		// tick 12000 == 18:00

		// offset ticks by 6 hours and modulus to 24 hours
		int hours = (int) ((ticks / 1000L + 6L) % 24L);
		// modulus to the hour, convert to minutes
		int minutes = (int) (60L * (ticks % 1000L) / 1000L);

		String shours = "" + hours;
		String sminutes = "" + minutes;
		if (minutes <= 9) {
			sminutes = "0" + minutes;
		}

		return shours + ":" + sminutes;
	}

	public static String ticksTo12h(long ticks) {
		// tick 0 == tick 24000 == 6:00
		// tick 12000 == 18:00

		// offset ticks by 6 hours and modulus to 24 hours
		int hours = (int) ((ticks / 1000L + 6L) % 24L);
		// modulus to the hour, convert to minutes
		int minutes = (int) (60L * (ticks % 1000L) / 1000L);

		int hours12 = hours % 12;

		String shours = "" + (hours12 == 0 ? 12 : hours12);
		String sminutes = "" + minutes;
		if (minutes <= 9) {
			sminutes = "0" + minutes;
		}
		 
		return shours + ":" + sminutes + " " + (hours >= 12 ? "pm" : "am");
	}
}
