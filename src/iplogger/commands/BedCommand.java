package iplogger.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import iplogger.CommandBase;

public class BedCommand extends CommandBase {

	public String commandName() { return "bed"; }

	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		
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
