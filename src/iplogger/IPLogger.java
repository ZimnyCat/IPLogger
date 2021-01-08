package iplogger;


import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class IPLogger extends JavaPlugin implements Listener {
	
	int counter = 0;
	int time = 21600;
	int tenCounter;
	boolean ten = false;
	HashMap<String, Boolean> killUsed = new HashMap<>();
	HashMap<String, Long> cooldown = new HashMap<>();
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		BukkitRunnable restart = new BukkitRunnable() {
			
			@Override
			public void run() {
				// high quality code!!!!
				counter++;
				if(counter == time - 300) {
					Bukkit.broadcastMessage(ChatColor.RED + "������ �������������� ����� " + ChatColor.WHITE + "5" + ChatColor.RED + " �����!");
				} if(counter == time - 60) {
					Bukkit.broadcastMessage(ChatColor.RED + "������ �������������� ����� " + ChatColor.WHITE + "1" + ChatColor.RED + " ������!");
				} if(counter == time - 30) {
					Bukkit.broadcastMessage(ChatColor.RED + "������ �������������� ����� " + ChatColor.WHITE + "30" + ChatColor.RED + " ������!");
				} if(counter == time - 10) {
					ten = true;
				} if(counter < time && ten == true) {
					tenCounter = time - counter;
					Bukkit.broadcastMessage(ChatColor.RED + "������ �������������� ����� " + ChatColor.WHITE + tenCounter);
				} if(counter == time) {
					getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
					Bukkit.getServer().shutdown();
				}
			}
		};
		restart.runTaskTimer(this, 20, 20);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		String name = e.getEntity().getDisplayName();
		if (killUsed.containsKey(name) && killUsed.get(name)) {
			e.setDeathMessage(name + " died");
			killUsed.put(name, false);
		}
	}
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		// command cooldown
		Player player = e.getPlayer();
		Long time = System.currentTimeMillis();
		if (cooldown.containsKey(player.getDisplayName()) 
				&& (time - cooldown.get(player.getDisplayName())) < 2000) {
			player.sendMessage(ChatColor.RED + "������� ������!");
			e.setCancelled(true);
			return;
		}
		cooldown.put(player.getDisplayName(), System.currentTimeMillis());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("kill")) {
			killUsed.put(player.getDisplayName(), true);
			player.setHealth(0);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("coords")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "/coords chat - ��������� ���� ���������� � ���");
				sender.sendMessage(ChatColor.GREEN + "/coords send <�����> - ��������� ���� ���������� ������� ������");
				return true;
			}
			Location loc = player.getLocation();
			String[] locArray = {" " + loc.getBlockX(), " " + loc.getBlockY(), " " + loc.getBlockZ()};
			switch (args[0]) {
			case "chat":
				Bukkit.broadcastMessage(ChatColor.GREEN + "���������� " + player.getDisplayName() + ":" + locArray[0] + locArray[1] + locArray[2]);
				break;
			case "send":
				String reciver;
				try {
					reciver = args[1];
				} catch (Exception e) {
					sender.sendMessage(ChatColor.GREEN + "����������� ��� ������!");
					return true;
				}
				try {
					Bukkit.getPlayer(reciver).sendMessage(ChatColor.GREEN + player.getDisplayName() + " �������� ��� ���� ����������:" + locArray[0] + locArray[1] + locArray[2]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.GREEN + "����� " + reciver + " �� ������");
					return true;
				}
				sender.sendMessage(ChatColor.GREEN + "���� ���������� ���������� " + reciver);
			return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("bed")) {
			try {
				Location bedLoc = player.getBedSpawnLocation();
				String[] bedLocArray = {" " + bedLoc.getBlockX(), " " + bedLoc.getBlockY(), " " + bedLoc.getBlockZ()};
				sender.sendMessage(ChatColor.RED + "���� ������� ��������� �� " + ChatColor.GREEN  + bedLocArray[0] + bedLocArray[1] + bedLocArray[2]);
			}
			catch(Exception e) {
				sender.sendMessage(ChatColor.RED + "���� ������� �� �������!");
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("vzlom")) {
			player.kickPlayer("�� �������� ������");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("discord")) {
			sender.sendMessage(ChatColor.AQUA + "Discord Server: https://discord.gg/SC5eyRKhRr");
			return true;
		}
		return true;
	}
}
