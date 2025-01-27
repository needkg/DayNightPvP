package me.needkg.daynightpvp;

import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.integration.worldguard.WorldGuardManager;
import me.needkg.daynightpvp.utis.logging.Logger;
import me.needkg.daynightpvp.utis.plugin.PluginValidator;
import me.needkg.daynightpvp.utis.plugin.StartupBanner;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightPvP extends JavaPlugin {

    public static boolean isVaultPresent;
    public static boolean isGriefPresent;
    public static boolean isWorldGuardPresent;
    public static boolean isPlaceholderPresent;

    private static DayNightPvP instance;

    public DayNightPvP() {
        instance = this;
    }

    public static DayNightPvP getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {

        verifyCompatibilityPlugins();

        if (isWorldGuardPresent) {
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

    private void verifyCompatibilityPlugins() {
        isVaultPresent = PluginValidator.exists("Vault");
        isWorldGuardPresent = PluginValidator.exists("WorldGuard");
        isGriefPresent = PluginValidator.exists("GriefPrevention");
        isPlaceholderPresent = PluginValidator.exists("PlaceholderAPI");
    }
}