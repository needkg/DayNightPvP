package me.needkg.daynightpvp.world;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;

import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.services.GlobalSettingsService;
import me.needkg.daynightpvp.feature.config.services.WorldSettingsService;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.models.DnpWorld;

public class WorldInitializer {

    private final Logger logger;
    private final ResourceFile resourceFile;
    private final GlobalSettingsService globalSettingsService;
    private Set<DnpWorld> avaliableWorlds = new HashSet<>();

    public WorldInitializer(Logger logger, ResourceFile resourceFile, GlobalSettingsService globalSettingsService) {
        this.logger = logger;
        this.resourceFile = resourceFile;
        this.globalSettingsService = globalSettingsService;
    }

    public void initialize() {

        Set<String> configuredWorlds = globalSettingsService.get().worlds();
        int configuredWorldsSize = configuredWorlds.size();
        int currentWorld = 1;

        for (String worldName : configuredWorlds) {

            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                logger.warn("Skipping world '" + worldName + "', world was not found (" + currentWorld + "/" + configuredWorldsSize + ")");
                currentWorld++;
                continue;
            }

            logger.warn("Initializing world '" + worldName + "' (" + currentWorld + "/" + configuredWorldsSize + ")");
            currentWorld++;
            DnpWorld dnpWorld = new DnpWorld(worldName, world.getEnvironment(), new WorldSettingsService(resourceFile, worldName));
            avaliableWorlds.add(dnpWorld);

        }

        if (avaliableWorlds.size() == 0) {
            logger.warn("No worlds were initialized");
        } else {
            logger.info("Successfully initialized (" + avaliableWorlds.size() + "/" + configuredWorldsSize + ") worlds");
        }
        
    }
    
}
