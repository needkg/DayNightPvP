package me.needkg.daynightpvp.world;

import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.settings.WorldSettingsGateway;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class WorldLoader implements Initializable, Reloadable {

    private final Logger logger;
    private final WorldSettingsGateway worldRepository;

    public WorldLoader(Logger logger, WorldSettingsGateway worldRepository) {
        this.logger = logger;
        this.worldRepository = worldRepository;
    }

    @Override
    public void init() {

        Set<DnpWorld> setDnpWorld = new HashSet<>();

        worldRepository.findConfiguredWorlds().ifPresent(configuredWorlds -> {
            configuredWorlds.forEach(worldName -> {
                Optional<DnpWorld> dnpWorldOptional = worldRepository.findDnpWorld(worldName);
                dnpWorldOptional.ifPresentOrElse(dnpWorld -> {
                    logger.info("Initializing world '" + worldName + "'...");
                    setDnpWorld.add(dnpWorld);
                }, () -> logger.warn("Skipping world '" + worldName + "' initialization because it, world was not found"));
            });
        });

        if (setDnpWorld.size() == 0) {
            logger.warn("No worlds were found, verify that the worlds are configured correctly in 'worlds.yml' and that they are loaded in the server");
        }

    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }

}