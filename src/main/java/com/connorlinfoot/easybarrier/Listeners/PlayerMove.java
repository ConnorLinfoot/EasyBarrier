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
import java.util.List;
import java.util.Set;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String mat = EasyBarrier.getPlugin().getConfig().getString("Barrier Block");
        //player.sendMessage("Event is being called");
        //player.sendMessage(mat);
        //player.sendMessage(String.valueOf(event.getTo().getBlock().getType()));
        Block closest = closestBlock(event.getTo(), new HashSet<Integer>(Arrays.asList(Material.getMaterial(mat).getId())));
        if (closest != null) {
            event.setCancelled(true);
        }
    }

    private static Block closestBlock(Location origin, Set<Integer> types) {
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

            if (types.contains(world.getBlockTypeIdAt(x, testY, z))) return world.getBlockAt(x, testY, z);
        }

        return null;
    }

}
