package iplogger.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import iplogger.CommandBase;

public class CoordsCommand extends CommandBase {
	
	List<Player> ignoreList = new ArrayList<>();

	public String commandName() { return "coords"; }

	public void run(CommandSender sender, Command cmd, String lable, String[] args) {
		Player player = (Player)sender;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "/coords chat - отправить ваши координаты в чат");
			sender.sendMessage(ChatColor.GREEN + "/coords send <игрок> - отправить ваши координаты другому игроку");
			sender.sendMessage(ChatColor.GREEN + "/coords ignore - включить/выключить игнор сообщений от /coords, вызванных другими игроками");
			return;
		}
		Location loc = player.getLocation();
		String[] locArray = {" " + loc.getBlockX(), " " + loc.getBlockY(), " " + loc.getBlockZ()};
		switch (args[0].toLowerCase()) {
		case "chat":
			sendCoordsMsg(ChatColor.GREEN + "Координаты " + player.getDisplayName() + ":" + locArray[0] + locArray[1] + locArray[2], player, true);
			break;
		case "send":
			String reciver;
			try {
				reciver = args[1];
			} catch (Exception e) {
				sender.sendMessage(ChatColor.GREEN + "Отсутствует ник игрока!");
				return;
			}
			try {
				sendCoordsMsg(ChatColor.GREEN + player.getDisplayName() + " отправил вам свои координаты:" + locArray[0] + locArray[1] + locArray[2],
						Bukkit.getPlayer(reciver), false);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.GREEN + "Игрок " + reciver + " не найден");
				return;
			}
			sender.sendMessage(ChatColor.GREEN + "Ваши координаты отправлены " + reciver);
			break;
		case "ignore":
			if (ignoreList.contains(player)) {
				ignoreList.remove(player); 
				sender.sendMessage(ChatColor.GREEN + "Теперь вам будут показываться сообщения от /coords, вызванные другими игроками");
			}
			else {
				ignoreList.add(player);
				sender.sendMessage(ChatColor.GREEN + "Вам больше не будут показываться сообщения от /coords, вызванные другими игроками");
			}
		return;
		}
	}
	
	private void sendCoordsMsg(String msg, Player reciver, boolean broadcast) {
		if (broadcast == true) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (ignoreList.contains(player)) continue;
				player.sendMessage(msg);
			} return;
		}
		if (!ignoreList.contains(reciver)) reciver.sendMessage(msg);
	}
}
