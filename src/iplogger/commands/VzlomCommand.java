package iplogger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import iplogger.CommandBase;

public class VzlomCommand extends CommandBase {

	public String commandName() { return "vzlom"; }

	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		player.kickPlayer("вы взломали сервер");
	}
}
