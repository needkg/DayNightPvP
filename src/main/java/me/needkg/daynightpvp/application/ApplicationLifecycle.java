package me.needkg.daynightpvp.application;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.command.CommandInitializer;
import me.needkg.daynightpvp.feature.config.ResourceFile;
import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.settings.MessagesSettings;
import me.needkg.daynightpvp.feature.config.settings.MessagesSettingsGateway;
import me.needkg.daynightpvp.feature.config.settings.PluginSettings;
import me.needkg.daynightpvp.feature.config.settings.PluginSettingsGateway;
import me.needkg.daynightpvp.feature.gui.MenuManager;
import me.needkg.daynightpvp.feature.gui.MenuRenderer;
import me.needkg.daynightpvp.feature.gui.menus.MainMenu;
import me.needkg.daynightpvp.integrations.bstats.BStatsInitializer;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.lifecycle.Stoppable;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldLoader;
import me.needkg.daynightpvp.world.configuration.WorldSettingsGateway;

public class ApplicationLifecycle implements Initializable, Reloadable, Stoppable {

    private final JavaPlugin plugin;
    private final Logger logger;
    private ResourceLoader resourceLoader;
    private ApplicationState applicationState;
    
    public ApplicationLifecycle(JavaPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @Override
    public void init() {
        loadConfiguration();
        initWorlds();
        initMetrics();
        initCommands();
        logger.info("DayNightPvP enabled!");
    }

    private void loadConfiguration() {

        logger.info("Loading configuration...");

        resourceLoader = new ResourceLoader(plugin, logger);

        ResourceFile globalResource = resourceLoader.load("config.yml");
        PluginSettingsGateway pluginSettingsGateway = new PluginSettingsGateway(globalResource);
        PluginSettings pluginSettings = pluginSettingsGateway.findGlobalSettings();

        ResourceFile messagesResource = resourceLoader.load("lang/" + pluginSettings.language() + ".yml");
        MessagesSettingsGateway messagesSettingsGateway = new MessagesSettingsGateway(messagesResource);
        MessagesSettings messagesSettings = messagesSettingsGateway.findMessagesSettings();

        applicationState = new ApplicationState(messagesSettings, pluginSettings);
    }

    private void initWorlds() {
        logger.info("Initializing worlds...");

        ResourceFile worldResource = resourceLoader.load("worlds.yml");
        WorldSettingsGateway worldSettingsGateway = new WorldSettingsGateway(worldResource);

        WorldLoader worldLoader = new WorldLoader(logger, worldSettingsGateway);
        worldLoader.init();
    }

    private void initMetrics() {
        logger.info("Initializing metrics...");
        BStatsInitializer bStatsInitializer = new BStatsInitializer(plugin);
        bStatsInitializer.init();
    }

    private void initCommands() {
        logger.info("Initializing commands...");
        MenuRenderer menuRenderer = new MenuRenderer();
        MenuManager menuManager = new MenuManager(menuRenderer);
        MainMenu mainMenu = new MainMenu();
        CommandInitializer commandInitializer = new CommandInitializer(plugin, menuManager, mainMenu);
        commandInitializer.init();
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
