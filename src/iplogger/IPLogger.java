package iplogger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import iplogger.commands.BedCommand;
import iplogger.commands.CoordsCommand;
import iplogger.commands.KillCommand;
import iplogger.commands.VzlomCommand;

public class IPLogger extends JavaPlugin implements Listener {
	
	int counter = 0;
	int time = 21600;
	int tenCounter;
	boolean ten = false;
	
	HashMap<String, Long> cooldown = new HashMap<>();
	List<Player> ignoreList = new ArrayList<>();
	
	public static List<CommandBase> commands = Arrays.asList(new BedCommand(), new CoordsCommand(), new KillCommand(), new VzlomCommand());
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		BukkitRunnable restart = new BukkitRunnable() {
			
			@Override
			public void run() {
				// high quality code!!!!
				counter++;
				if(counter == time - 300) {
					Bukkit.broadcastMessage(ChatColor.RED + "Сервер перезапустится через " + ChatColor.WHITE + "5" + ChatColor.RED + " минут!");
				} if(counter == time - 60) {
					Bukkit.broadcastMessage(ChatColor.RED + "Сервер перезапустится через " + ChatColor.WHITE + "1" + ChatColor.RED + " минуту!");
				} if(counter == time - 30) {
					Bukkit.broadcastMessage(ChatColor.RED + "Сервер перезапустится через " + ChatColor.WHITE + "30" + ChatColor.RED + " секунд!");
				} if(counter == time - 10) {
					ten = true;
				} if(counter < time && ten == true) {
					tenCounter = time - counter;
					Bukkit.broadcastMessage(ChatColor.RED + "Сервер перезапустится через " + ChatColor.WHITE + tenCounter);
				} if(counter == time) {
					Bukkit.getServer().shutdown();
				}
			}
		};
		restart.runTaskTimer(this, 20, 20);
	}
	
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		// command cooldown
		Player player = e.getPlayer();
		Long time = System.currentTimeMillis();
		if (cooldown.containsKey(player.getDisplayName()) 
				&& (time - cooldown.get(player.getDisplayName())) < 2000) {
			player.sendMessage(ChatColor.RED + "Слишком быстро!");
			e.setCancelled(true);
			return;
		}
		cooldown.put(player.getDisplayName(), time);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		for (CommandBase command : commands) {
			if (cmd.getName().equalsIgnoreCase(command.commandName())) { command.run(sender, cmd, lable, args); }
		}
		return true;
	}
}
