package me.needkg.daynightpvp;

import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.features.worldguard.FlagManager;
import me.needkg.daynightpvp.utils.LoggingUtil;
import me.needkg.daynightpvp.utils.PluginUtil;
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
            FlagManager.register();
        }
    }

    @Override
    public void onEnable() {
        LoggingUtil.sendStartupMessage();

        DependencyContainer.initialize();
        DependencyContainer container = DependencyContainer.getInstance();

        container.getConfigManager().initializeFile();
        container.getLangManager().initializeFile();
        container.getBStatsHandler().start();
        container.getCommandHandler().register();
        container.getListenersHandler().register();
        container.getPlaceholderHandler().register();
        container.getRunnableHandler().startAllTasks();
    }

    private void verifyCompatibilityPlugins() {
        isVaultPresent = PluginUtil.isPluginInstalled("Vault");
        isWorldGuardPresent = PluginUtil.isPluginInstalled("WorldGuard");
        isGriefPresent = PluginUtil.isPluginInstalled("GriefPrevention");
        isPlaceholderPresent = PluginUtil.isPluginInstalled("PlaceholderAPI");
    }
}