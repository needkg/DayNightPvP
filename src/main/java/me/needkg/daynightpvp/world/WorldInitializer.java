package me.needkg.daynightpvp.world;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;

import me.needkg.daynightpvp.feature.config.providers.impl.GlobalSettingsProvider;
import me.needkg.daynightpvp.feature.config.providers.impl.WorldSettingsProvider;
import me.needkg.daynightpvp.feature.config.providers.models.ResourceFile;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.models.DnpWorld;

public class WorldInitializer implements Initializable, Reloadable {

    private final Logger logger;
    private final ResourceFile resourceFile;
    private final GlobalSettingsProvider globalSettingsService;
    private Set<DnpWorld> avaliableWorlds = new HashSet<>();

    public WorldInitializer(Logger logger, ResourceFile resourceFile, GlobalSettingsProvider globalSettingsService) {
        this.logger = logger;
        this.resourceFile = resourceFile;
        this.globalSettingsService = globalSettingsService;
    }

    @Override
    public void init() {

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
            DnpWorld dnpWorld = new DnpWorld(worldName, world.getEnvironment(), new WorldSettingsProvider(resourceFile, worldName));
            avaliableWorlds.add(dnpWorld);

        }

        if (avaliableWorlds.size() == 0) {
            logger.warn("No worlds were initialized");
        } else {
            logger.info("Successfully initialized worlds");
        }
        
    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }
    
}