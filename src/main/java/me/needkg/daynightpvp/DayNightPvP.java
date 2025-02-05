package me.needkg.daynightpvp;

import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.integration.worldguard.WorldGuardManager;
import me.needkg.daynightpvp.utils.logging.Logger;
import me.needkg.daynightpvp.utils.plugin.PluginValidator;
import me.needkg.daynightpvp.utils.plugin.StartupBanner;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightPvP extends JavaPlugin {

    private static DayNightPvP instance;

    public DayNightPvP() {
        instance = this;
    }

    public static DayNightPvP getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {

        if (PluginValidator.isWorldGuardPresent()) {
            WorldGuardManager.register();
        }
    }

    @Override
    public void onEnable() {
        StartupBanner.display();

        DependencyContainer.initializeContainer();
        DependencyContainer container = DependencyContainer.getInstance();

        container.getConfigurationFile().initializeFile();
        Logger.debug("Dependency injection container started.");
        Logger.debug("Configuration file loaded.");

        Logger.debug("Loading language files...");
        container.getLanguageFile().initializeFile();

        Logger.debug("Setting up initial world states...");
        container.getWorldStateManager().initializeWorldStates();

        Logger.debug("Starting metrics...");
        container.getMetricsManager().start();

        Logger.debug("Registering commands...");
        container.getCommandManager().register();

        Logger.debug("Registering event listeners...");
        container.getListenerManager().register();

        Logger.debug("Starting scheduled tasks...");
        container.getTaskManager().startAllTasks();

        Logger.debug("Registering placeholders...");
        container.getPlaceholderHandler().register();
    }

    @Override
    public void onDisable() {
        DependencyContainer container = DependencyContainer.getInstance();

        container.getListenerManager().unregisterAll();
        container.getPlaceholderHandler().unregister();
        container.getTaskManager().stopAllTasks();
    }

}