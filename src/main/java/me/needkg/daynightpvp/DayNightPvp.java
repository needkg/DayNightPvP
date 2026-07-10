package me.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.loader.ConfigLoader;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.services.GlobalSettingsService;
import me.needkg.daynightpvp.feature.config.services.MessagesConfigService;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldInitializer;

public final class DayNightPvp extends JavaPlugin {

    private Logger logger;
    private GlobalSettingsService globalSettingsService;
    private MessagesConfigService messagesConfigService;

    @Override
    public void onEnable() {

        logger = new Logger(this);

        logger.info("Loading resource...");
        ConfigLoader configLoader = new ConfigLoader(this);

        logger.info("Loading config file (1/2)");
        ResourceFile configResource = configLoader.initializeFile("config.yml");
        globalSettingsService = new GlobalSettingsService(configResource);

        logger.info("Loading language file (2/2)");
        ResourceFile messagesResource = configLoader.initializeFile("lang/" + globalSettingsService.get().language() + ".yml");
        messagesConfigService = new MessagesConfigService(messagesResource);
        logger.info("Resources loaded.");

        logger.info("Loading metrics...");
        MetricsInitializer metricsInitializer = new MetricsInitializer(this);
        metricsInitializer.initialize();
        logger.info("Metrics loaded.");

        logger.info("Initializing worlds...");
        WorldInitializer worldInitializer = new WorldInitializer(logger, configResource, globalSettingsService);
        worldInitializer.initialize();

        logger.info("DayNightPvP enabled!");

    }

    @Override
    public void onDisable() {
    }
}
