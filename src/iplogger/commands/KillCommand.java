package iplogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import iplogger.CommandBase;

public class KillCommand extends CommandBase {
	
	public String commandName() { return "kill"; }
	
	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		player.setHealth(0);
	}
}
