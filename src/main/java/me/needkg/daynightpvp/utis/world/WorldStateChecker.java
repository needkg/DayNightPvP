package me.needkg.daynightpvp.utis.world;

import me.needkg.daynightpvp.task.controller.world.WorldStateController;
import me.needkg.daynightpvp.utis.search.WorldCollectionSearcher;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldStateChecker {

    public static boolean isInDayWorld(Player player) {
        World world = player.getWorld();
        return WorldCollectionSearcher.containsWorld(WorldStateController.dayWorlds, world);
    }

}
