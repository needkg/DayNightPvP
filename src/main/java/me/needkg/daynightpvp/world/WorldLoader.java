package me.needkg.daynightpvp.world;

import me.needkg.daynightpvp.feature.config.repository.WorldRepository;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.models.DnpWorld;

import java.util.Optional;

public class WorldLoader implements Initializable, Reloadable {

    private final Logger logger;
    private final WorldManager worldManager;
    private final WorldRepository worldRepository;

    public WorldLoader(Logger logger, WorldManager worldManager, WorldRepository worldRepository) {
        this.logger = logger;
        this.worldManager = worldManager;
        this.worldRepository = worldRepository;
    }

    @Override
    public void init() {

        worldRepository.findConfiguredWorlds().ifPresent(configuredWorlds -> {
            configuredWorlds.forEach(worldName -> {
                Optional<DnpWorld> dnpWorldOptional = worldRepository.findDnpWorld(worldName);
                dnpWorldOptional.ifPresentOrElse(dnpWorld -> {
                    logger.info("Initializing world '" + worldName + "'");
                    worldManager.addWorld(dnpWorld);
                }, () -> logger.warn("Skipping world '" + worldName + "', world was not found"));
            });
        });

        if (worldManager.getWorldCount() == 0) {
            logger.warn("No worlds were found, verify that the worlds are configured correctly in 'worlds.yml' and that they are loaded in the server");
        }

    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }

}