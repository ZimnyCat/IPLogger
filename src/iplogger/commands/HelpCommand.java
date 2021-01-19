package iplogger.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import iplogger.CommandBase;
import iplogger.IPLogger;

public class HelpCommand extends CommandBase {
	
	public String name() { return "help"; }
	
	public String description() { return "Получить помощь"; }
	
	List<String> help = Arrays.asList(
			"---Помощь---",
			"/ignore <никнейм> - игнорировать/перестать игнорировать игрока",
			"/msg <никнейм> <собщение> - отправить личное сообщение игроку",
			"/r - отправить сообщение последнему написавшему вам игроку"
			);
	
	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		for (String msg : help) sender.sendMessage(ChatColor.GREEN + msg);
		for (CommandBase command : IPLogger.commands) sender.sendMessage(ChatColor.GREEN + "/" + command.name() + " - " + command.description());
	}
}
