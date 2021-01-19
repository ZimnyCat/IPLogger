package iplogger.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import iplogger.CommandBase;

public class BedCommand extends CommandBase {

	public String name() { return "bed"; }
	
	public String description() { return "Отправляет вам местоположение вашей кровати"; }

	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		
		if (player.getWorld().getEnvironment() != Environment.NORMAL) {
			sender.sendMessage(ChatColor.RED + "Вы не находитесь в обычном мире!");
			return;
		}
		try {
			Location bedLoc = player.getBedSpawnLocation();
			String[] bedLocArray = {" " + bedLoc.getBlockX(), " " + bedLoc.getBlockY(), " " + bedLoc.getBlockZ()};
			sender.sendMessage(ChatColor.RED + "Ваша кровать находится на " + ChatColor.GREEN  + bedLocArray[0] + bedLocArray[1] + bedLocArray[2]);
		}
		catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "Ваша кровать не найдена!");
		}
	}
}
