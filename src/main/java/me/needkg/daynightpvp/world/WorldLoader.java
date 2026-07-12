package me.needkg.daynightpvp.world;

import me.needkg.daynightpvp.feature.config.repository.WorldRepository;
import me.needkg.daynightpvp.shared.lifecycle.Loadable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.models.DnpWorld;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class WorldLoader implements Loadable<Set<DnpWorld>, Optional<Set<String>>>, Reloadable {

    private final Logger logger;
    private final WorldRepository worldRepository;
    private Set<DnpWorld> avaliableDnpWorlds = new HashSet<>();

    public WorldLoader(Logger logger, WorldRepository worldRepository) {
        this.logger = logger;
        this.worldRepository = worldRepository;
    }

    @Override
    public Set<DnpWorld> load(Optional<Set<String>> configuredWorldsOptional) {

        configuredWorldsOptional.ifPresent(configuredWorlds -> {
            configuredWorlds.stream().forEach(worldName -> {
                Optional<DnpWorld> dnpWorldOptional = worldRepository.findDnpWorld(worldName);
                dnpWorldOptional.ifPresentOrElse(dnpWorld -> {
                    logger.info("Initializing world '" + worldName + "'");
                    avaliableDnpWorlds.add(dnpWorld);
                }, () -> logger.warn("Skipping world '" + worldName + "', world was not found"));
            });
        });

        if (avaliableDnpWorlds.size() == 0) {
            logger.warn("No worlds were found, verify that the worlds are configured correctly in 'worlds.yml' and that they are loaded in the server");
        } else {
            logger.info("Successfully initialized worlds");
        }

        return avaliableDnpWorlds;

    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }

}