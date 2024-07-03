package org.callvdois.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.Listeners.ListenersHandler;
import org.callvdois.daynightpvp.commands.CommandHandler;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.metrics.MetricsHandler;
import org.callvdois.daynightpvp.placeholder.PlaceholderHandler;
import org.callvdois.daynightpvp.runnables.RunnableHandler;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PluginUtils;
import org.callvdois.daynightpvp.worldguard.FlagHandler;

import java.util.ArrayList;
import java.util.List;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;
    public static List<BukkitTask> serviceTasks = new ArrayList<>();
    private static DayNightPvP instance;
    private final ConsoleUtils consoleUtils;
    private final ConfigFile configFile;
    private final LangFile langFile;
    private final CommandHandler commandHandler;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final FlagHandler flagHandler;
    private final RunnableHandler runnableHandler;
    private final MetricsHandler metricsHandler;
    private final PluginUtils pluginUtils;

    public DayNightPvP() {
        instance = this;

        consoleUtils = new ConsoleUtils();
        configFile = new ConfigFile();
        langFile = new LangFile();
        commandHandler = new CommandHandler();
        listenersHandler = new ListenersHandler();
        placeholderHandler = new PlaceholderHandler();
        flagHandler = new FlagHandler();
        runnableHandler = new RunnableHandler();
        metricsHandler = new MetricsHandler();
        pluginUtils = new PluginUtils();

    }

    public static DayNightPvP getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        verifyCompatibilityPlugins();

        if (worldGuardIsPresent) {
            flagHandler.register();
        }
    }

    @Override
    public void onEnable() {
        consoleUtils.sendStartupMessage();

        metricsHandler.start();

        configFile.createFile();
        langFile.createFile();

        commandHandler.register();

        listenersHandler.register();

        placeholderHandler.register();

        runnableHandler.startAllRunnables();
    }


    private void verifyCompatibilityPlugins() {
        vaultIsPresent = pluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = pluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = pluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = pluginUtils.isPluginInstalled("PlaceholderAPI");
    }

    @Override
    public void onDisable() {
        runnableHandler.stopAllRunnables();
    }

}