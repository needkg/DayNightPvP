package com.needkg.daynightpvp;

import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.utils.LoggingUtils;
import com.needkg.daynightpvp.utils.PluginUtils;
import com.needkg.daynightpvp.worldguard.FlagHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;

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

        if (worldGuardIsPresent) {
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
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");
    }
}