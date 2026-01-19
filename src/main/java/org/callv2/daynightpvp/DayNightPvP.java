package org.callv2.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.callv2.daynightpvp.bstats.BStatsHandler;
import org.callv2.daynightpvp.commands.CommandHandler;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.listeners.ListenersHandler;
import org.callv2.daynightpvp.placeholder.PlaceholderHandler;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.utils.LoggingUtils;
import org.callv2.daynightpvp.utils.PluginUtils;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;
import org.callv2.daynightpvp.worldguard.FlagHandler;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;

    private static DayNightPvP instance;

    private final ConfigFile configFile;
    private final LangFile langFile;
    private final CommandHandler commandHandler;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;
    private final BStatsHandler bStatsHandler;

    public DayNightPvP() {
        instance = this;

        configFile = new ConfigFile();
        langFile = new LangFile(configFile);

        runnableHandler = new RunnableHandler(configFile, langFile);

        LoseMoneyOnDeath loseMoneyOnDeath = new LoseMoneyOnDeath(configFile, langFile);

        commandHandler = new CommandHandler(langFile, configFile, runnableHandler, loseMoneyOnDeath);

        listenersHandler = new ListenersHandler(configFile, langFile, loseMoneyOnDeath);
        placeholderHandler = new PlaceholderHandler(langFile, configFile);
        bStatsHandler = new BStatsHandler();

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

        configFile.createFile();
        langFile.createFile();

        bStatsHandler.start();

        commandHandler.register();

        listenersHandler.register();

        placeholderHandler.register();

        runnableHandler.startAllRunnables();
    }

    @Override
    public void onDisable() {
        runnableHandler.stopAllRunnables();
    }

    private void verifyCompatibilityPlugins() {
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");
    }

}