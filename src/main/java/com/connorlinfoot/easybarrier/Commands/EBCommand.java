package com.connorlinfoot.easybarrier.Commands;

import com.connorlinfoot.easybarrier.EasyBarrier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("block")) {
            if (!sender.hasPermission("easybarrier.block")) {
                sender.sendMessage(EasyBarrier.Prefix + ChatColor.RED + "You do not have the permission \"" + ChatColor.BOLD + "easybarrier.block" + ChatColor.RESET + ChatColor.RED + "\"");
                return false;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(EasyBarrier.Prefix + ChatColor.RED + "This command can only be ran as a player");
                return false;
            }

            Player player = (Player) sender;
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getType() == null || itemStack.getType() == Material.AIR) {
                sender.sendMessage(EasyBarrier.Prefix + ChatColor.RED + "Your hand is empty!");
                return false;
            }

            EasyBarrier.getPlugin().getConfig().set("Barrier Block", itemStack.getType().toString());
            EasyBarrier.getPlugin().saveConfig();

            String mat = EasyBarrier.getPlugin().getConfig().getString("Barrier Block");
            EasyBarrier.Barrier = Material.getMaterial(mat);
            if (EasyBarrier.Barrier == null)
                EasyBarrier.Barrier = Material.NETHER_FENCE;

            sender.sendMessage(EasyBarrier.Prefix + ChatColor.GREEN + "You have updated the block for EasyBarrier and the config has been reloaded");
            return true;
        }
        sender.sendMessage(ChatColor.AQUA + "\"" + EasyBarrier.getPlugin().getDescription().getName() + "\" - Version: " + EasyBarrier.getPlugin().getDescription().getVersion());
        return true;
    }

}
