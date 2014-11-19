package com.connorlinfoot.maxplayerbypass.Commands;

import com.connorlinfoot.maxplayerbypass.EasyBarrier;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "\"" + EasyBarrier.getPlugin().getDescription().getName() + "\" - Version: " + EasyBarrier.getPlugin().getDescription().getVersion());
        return true;
    }

}
