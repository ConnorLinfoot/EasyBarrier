package com.connorlinfoot.easybarrier.Listeners;

import com.connorlinfoot.easybarrier.EasyBarrier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("easybarrier.bypass")) return;
        String mat = EasyBarrier.getPlugin().getConfig().getString("Barrier Block");
        Material material = Material.getMaterial(mat);
        if (material == null)
            material = Material.NETHER_FENCE;
        boolean aboveY = EasyBarrier.getPlugin().getConfig().getBoolean("Check Above Y Only");
        Block closest = getBlockY(event.getTo(), new HashSet<Integer>(Arrays.asList(material.getId())), aboveY);
        if (closest != null) {
            event.setCancelled(true);
        }
    }

    private static Block getBlockY(Location origin, Set<Integer> types, boolean aboveOnly) {
        int x = origin.getBlockX();
        int y = origin.getBlockY();
        int z = origin.getBlockZ();
        World world = origin.getWorld();

        for (int cy = 2; cy < 512; cy++) {
            int testY;
            if ((cy & 1) == 0) {
                testY = y + cy / 2;
                if (testY > 255)
                    continue;
            } else {
                testY = y - cy / 2;
                if (testY < 0)
                    continue;
            }

            if (types.contains(world.getBlockTypeIdAt(x, testY, z))) {
                if (aboveOnly && testY > y) return null;
                return world.getBlockAt(x, testY, z);
            }
        }

        return null;
    }

}
