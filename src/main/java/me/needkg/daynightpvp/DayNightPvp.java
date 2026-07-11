package me.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.services.GlobalSettingsService;
import me.needkg.daynightpvp.feature.config.services.MessagesConfigService;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldInitializer;

public final class DayNightPvp extends JavaPlugin {

    private Logger logger;
    private ResourceLoader resourceLoader;
    private ResourceFile configResource;
    private GlobalSettingsService globalSettingsService;
    private ResourceFile messageResource;
    private MessagesConfigService messagesConfigService;
    private MetricsInitializer metricsInitializer;
    private WorldInitializer worldInitializer;


    @Override
    public void onEnable() {

        logger = new Logger(this);

        logger.info("Loading resources...");
        resourceLoader = new ResourceLoader(this);

        logger.info("Loading config file (1/2)");
        configResource = resourceLoader.load("config.yml");
        globalSettingsService = new GlobalSettingsService(configResource);

        logger.info("Loading language file (2/2)");
        messageResource = resourceLoader.load("lang/" + globalSettingsService.get().language() + ".yml");
        messagesConfigService = new MessagesConfigService(messageResource);
        logger.info("Resources loaded.");

        logger.info("Loading metrics...");
        metricsInitializer = new MetricsInitializer(this);
        metricsInitializer.initialize();
        logger.info("Metrics loaded.");

        logger.info("Initializing worlds...");
        worldInitializer = new WorldInitializer(logger, configResource, globalSettingsService);
        worldInitializer.init();

        logger.info("DayNightPvP enabled!");

    }

    @Override
    public void onDisable() {
    }
}
