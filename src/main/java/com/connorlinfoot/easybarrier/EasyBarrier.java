package com.connorlinfoot.easybarrier;

import com.connorlinfoot.easybarrier.Commands.EBCommand;
import com.connorlinfoot.easybarrier.Listeners.PlayerMove;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class EasyBarrier extends JavaPlugin {
    private static Plugin plugin;
    public static boolean SNAPSHOT = false;
    public static String Prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "EasyBarrier" + ChatColor.GRAY + "] " + ChatColor.RESET;
    public static Material Barrier = null;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        String mat = EasyBarrier.getPlugin().getConfig().getString("Barrier Block");
        Barrier = Material.getMaterial(mat);
        if (Barrier == null)
            Barrier = Material.NETHER_FENCE;

        console.sendMessage("");
        console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        console.sendMessage("");
        console.sendMessage(ChatColor.AQUA + getDescription().getName());
        console.sendMessage(ChatColor.AQUA + "Version " + getDescription().getVersion());
        if (getDescription().getVersion().contains("SNAPSHOT")) {
            SNAPSHOT = true;
            console.sendMessage(ChatColor.RED + "You are running a snapshot build of " + getDescription().getName() + " please report bugs!");
            console.sendMessage(ChatColor.RED + "NO support will be given if running old snapshot build!");
        }
        console.sendMessage("");
        console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        console.sendMessage("");

        registerCommands(console);
        registerEvents(console);
    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void registerEvents(ConsoleCommandSender console) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerMove(), this);
        console.sendMessage(Prefix + "Events have been registered");
    }

    private void registerCommands(ConsoleCommandSender console) {
        getCommand("eb").setExecutor(new EBCommand());
        console.sendMessage(Prefix + "Commands have been registered");
    }
}
