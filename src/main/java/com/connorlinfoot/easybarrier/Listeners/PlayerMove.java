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
        String mat = EasyBarrier.getPlugin().getConfig().getString("Barrier Block");
        boolean aboveY = EasyBarrier.getPlugin().getConfig().getBoolean("Check Above Y Only");
        Block closest = getBlockY(event.getTo(), new HashSet<Integer>(Arrays.asList(Material.getMaterial(mat).getId())), aboveY, player.getLocation());
        if (closest != null) {
            event.setCancelled(true);
        }
    }

    private static Block getBlockY(Location origin, Set<Integer> types, boolean aboveOnly, Location pLocation) {
        int x = origin.getBlockX();
        int y = origin.getBlockY();
        int z = origin.getBlockZ();
        int py = pLocation.getBlockY();
        World world = origin.getWorld();

        for (int cy = 2; cy < 512; cy++) {
            if (aboveOnly && py < y) break;
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

            if (types.contains(world.getBlockTypeIdAt(x, testY, z))) return world.getBlockAt(x, testY, z);
        }

        return null;
    }

}
