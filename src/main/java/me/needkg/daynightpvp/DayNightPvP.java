package me.needkg.daynightpvp;

import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.utils.LoggingUtils;
import me.needkg.daynightpvp.utils.PluginUtils;
import me.needkg.daynightpvp.worldguard.FlagHandler;
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
            FlagHandler.register();
        }
    }

    @Override
    public void onEnable() {
        LoggingUtils.sendStartupMessage();

        // Inicializa o container de dependências
        DependencyContainer.initialize();
        DependencyContainer container = DependencyContainer.getInstance();

        // Inicializa os componentes usando o container
        container.getConfigManager().createFile();
        container.getLangManager().createFile();
        container.getBStatsHandler().start();
        container.getCommandHandler().register();
        container.getListenersHandler().register();
        container.getPlaceholderHandler().register();
        container.getRunnableHandler().startAllRunnables();
    }

    private void verifyCompatibilityPlugins() {
        isVaultPresent = PluginUtils.isPluginInstalled("Vault");
        isWorldGuardPresent = PluginUtils.isPluginInstalled("WorldGuard");
        isGriefPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        isPlaceholderPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");
    }
}