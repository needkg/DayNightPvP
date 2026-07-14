package me.needkg.daynightpvp.application;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.command.CommandInitializer;
import me.needkg.daynightpvp.feature.config.ResourceFile;
import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.settings.MessagesSettings;
import me.needkg.daynightpvp.feature.config.settings.MessagesSettingsGateway;
import me.needkg.daynightpvp.feature.config.settings.PluginSettings;
import me.needkg.daynightpvp.feature.config.settings.PluginSettingsGateway;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.lifecycle.Stoppable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldLoader;
import me.needkg.daynightpvp.world.settings.WorldSettingsGateway;

public class ApplicationLifecycle implements Initializable, Reloadable, Stoppable {

    private final JavaPlugin plugin;
    private final Logger logger;
    private ResourceLoader resourceLoader;
    private ResourceFile globalResource;
    private PluginSettingsGateway pluginSettingsGateway;
    private PluginSettings pluginSettings;
    private ResourceFile messagesResource;
    private MessagesSettingsGateway messagesSettingsGateway;
    private MessagesSettings messagesSettings;
    private ResourceFile worldResource;
    private WorldSettingsGateway worldSettingsGateway;
    private WorldLoader worldLoader;
    private MetricsInitializer metricsInitializer;
    private CommandInitializer commandInitializer;
    
    public ApplicationLifecycle(JavaPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @Override
    public void init() {
        loadResources();
        initWorlds();
        initMetrics();
        initCommands();
        logger.info("DayNightPvP enabled!");
    }

    private void loadResources() {

        logger.info("Loading plugin resources...");

        resourceLoader = new ResourceLoader(plugin, logger);

        globalResource = resourceLoader.load("config.yml");
        pluginSettingsGateway = new PluginSettingsGateway(globalResource);
        pluginSettings = pluginSettingsGateway.findGlobalSettings();

        messagesResource = resourceLoader.load("lang/" + pluginSettings.language() + ".yml");
        messagesSettingsGateway = new MessagesSettingsGateway(messagesResource);
        messagesSettings = messagesSettingsGateway.findMessagesSettings();

        logger.info("Plugin resources loaded!");
    }

    private void initWorlds() {
        logger.info("Initializing worlds...");

        worldResource = resourceLoader.load("worlds.yml");
        worldSettingsGateway = new WorldSettingsGateway(worldResource);

        worldLoader = new WorldLoader(logger, worldSettingsGateway);
        worldLoader.init();

        logger.info("Worlds initialized!");
    }

    private void initMetrics() {
        logger.info("Initializing metrics...");
        metricsInitializer = new MetricsInitializer(plugin);
        metricsInitializer.init();
        logger.info("Metrics initialized!");
    }

    private void initCommands() {
        logger.info("Initializing commands...");
        commandInitializer = new CommandInitializer();
        commandInitializer.init();
        logger.info("Commands initialized!");
    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

}
