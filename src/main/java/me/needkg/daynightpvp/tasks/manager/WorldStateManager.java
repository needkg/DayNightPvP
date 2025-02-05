package me.needkg.daynightpvp.tasks.manager;

import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.tasks.enums.WorldState;
import org.bukkit.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorldStateManager {

    private final Map<World, WorldState> worldStateMap = new ConcurrentHashMap<>();

    private final GlobalConfigurationManager globalConfigurationManager;
    private final WorldConfigurationManager worldConfigurationManager;

    public WorldStateManager(GlobalConfigurationManager globalConfigurationManager, WorldConfigurationManager worldConfigurationManager) {
        this.globalConfigurationManager = globalConfigurationManager;
        this.worldConfigurationManager = worldConfigurationManager;
    }

    public void setWorldState(World world, WorldState worldState) {
        worldStateMap.put(world, worldState);
    }

    public WorldState getWorldState(World world) {
        return worldStateMap.get(world);
    }

    public void clearWorldStates() {
        worldStateMap.clear();
    }

    public void initializeWorldStates() {
        for (World world : globalConfigurationManager.getEnabledWorlds()) {
            String worldName = world.getName();
            long worldTime = world.getTime();

            if (worldConfigurationManager.isPvpAutomaticEnabled(worldName)) {
                worldStateMap.put(world, worldTime < worldConfigurationManager.getPvpAutomaticDayEnd(worldName) ? WorldState.DAY : WorldState.NIGHT);
            }
        }
    }

}
