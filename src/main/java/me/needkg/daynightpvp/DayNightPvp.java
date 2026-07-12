package me.needkg.daynightpvp;

import me.needkg.daynightpvp.feature.command.CommandInitializer;
import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.models.GlobalSettings;
import me.needkg.daynightpvp.feature.config.models.MessagesSettings;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.repository.GlobalRepository;
import me.needkg.daynightpvp.feature.config.repository.MessagesRepository;
import me.needkg.daynightpvp.feature.config.repository.WorldRepository;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class DayNightPvp extends JavaPlugin {

    private Logger logger;
    private ResourceLoader resourceLoader;
    private ResourceFile globalResource;
    private GlobalRepository globalRepository;
    private GlobalSettings globalSettings;
    private ResourceFile messagesResource;
    private MessagesRepository messagesRepository;
    private MessagesSettings messagesSettings;
    private ResourceFile worldResource;
    private WorldRepository worldRepository;
    private MetricsInitializer metricsInitializer;
    private WorldLoader worldLoader;
    private CommandInitializer commandInitializer;


    @Override
    public void onEnable() {

        logger = new Logger(this);

        logger.info("Loading resources...");
        resourceLoader = new ResourceLoader(this);

        logger.info("Loading 'config.yml' file");
        globalResource = resourceLoader.load("config.yml");
        globalRepository = new GlobalRepository(globalResource);
        globalSettings = globalRepository.findGlobalSettings();

        logger.info("Loading '" + globalSettings.language() + ".yml' file");
        messagesResource = resourceLoader.load("lang/" + globalSettings.language() + ".yml");
        messagesRepository = new MessagesRepository(messagesResource);
        messagesSettings = messagesRepository.findMessagesSettings();

        logger.info("Loading 'worlds.yml' file (3/3)");
        worldResource = resourceLoader.load("worlds.yml");
        worldRepository = new WorldRepository(worldResource);

        logger.info("Resources loaded.");

        logger.info("Loading metrics...");
        metricsInitializer = new MetricsInitializer(this);
        metricsInitializer.init();
        logger.info("Metrics loaded.");

        logger.info("Initializing worlds...");
        worldLoader = new WorldLoader(logger, worldRepository);
        worldLoader.load(worldRepository.findConfiguredWorlds());

        logger.info("Registring commands...");
        commandInitializer = new CommandInitializer(logger, messagesSettings);
        commandInitializer.init();
        logger.info("Commands registered.");

        logger.info("DayNightPvP enabled!");

    }

    @Override
    public void onDisable() {
    }
}
