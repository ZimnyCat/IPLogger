package iplogger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandBase {
	public abstract String name();
	public abstract String description();
	public abstract void run(CommandSender sender, Command cmd, String lable, String[] args);
}
