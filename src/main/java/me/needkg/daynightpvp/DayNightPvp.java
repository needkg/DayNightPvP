package me.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.command.CommandInitializer;
import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.providers.GlobalSettingsProvider;
import me.needkg.daynightpvp.feature.config.providers.MessagesConfigProvider;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldInitializer;

public final class DayNightPvp extends JavaPlugin {

    private Logger logger;
    private ResourceLoader resourceLoader;
    private ResourceFile configResource;
    private GlobalSettingsProvider globalSettingsProvider;
    private ResourceFile messageResource;
    private MessagesConfigProvider messagesConfigProvider;
    private MetricsInitializer metricsInitializer;
    private WorldInitializer worldInitializer;
    private CommandInitializer commandInitializer;


    @Override
    public void onEnable() {

        logger = new Logger(this);

        logger.info("Loading resources...");
        resourceLoader = new ResourceLoader(this);

        logger.info("Loading config file (1/2)");
        configResource = resourceLoader.load("config.yml");
        globalSettingsProvider = new GlobalSettingsProvider(configResource);

        logger.info("Loading language file (2/2)");
        messageResource = resourceLoader.load("lang/" + globalSettingsProvider.get().language() + ".yml");
        messagesConfigProvider = new MessagesConfigProvider(messageResource);
        logger.info("Resources loaded.");

        logger.info("Loading metrics...");
        metricsInitializer = new MetricsInitializer(this);
        metricsInitializer.initialize();
        logger.info("Metrics loaded.");

        logger.info("Initializing worlds...");
        worldInitializer = new WorldInitializer(logger, configResource, globalSettingsProvider);
        worldInitializer.init();

        logger.info("Registring commands...");
        commandInitializer = new CommandInitializer(logger, messagesConfigProvider);
        commandInitializer.init();
        logger.info("Commands registred.");

        logger.info("DayNightPvP enabled!");

    }

    @Override
    public void onDisable() {
    }
}
