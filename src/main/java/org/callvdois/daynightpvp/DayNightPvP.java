package org.callvdois.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.commands.RegisterCommand;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.events.RegisterEvents;
import org.callvdois.daynightpvp.metrics.RegisterMetrics;
import org.callvdois.daynightpvp.placeholder.RegisterPlaceHolder;
import org.callvdois.daynightpvp.service.ServiceManager;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PluginUtils;
import org.callvdois.daynightpvp.worldguard.RegisterCustomFlag;

import java.util.ArrayList;
import java.util.List;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;
    public static List<BukkitTask> serviceTasks = new ArrayList<>();
    private static DayNightPvP instance;
    private final FilesManager filesManager;
    private final RegisterCommand registerCommand;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final RegisterCustomFlag registerCustomFlag;
    private final ServiceManager serviceManager;
    private final RegisterMetrics registerMetrics;

    public DayNightPvP() {
        instance = this;

        filesManager = new FilesManager();
        registerCommand = new RegisterCommand();
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        registerCustomFlag = new RegisterCustomFlag();
        serviceManager = new ServiceManager();
        registerMetrics = new RegisterMetrics();

    }

    public static DayNightPvP getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    private void load() {

        verifyCompabilityPlugins();

        if (worldGuardIsPresent) {
            registerCustomFlag.run();
        }
    }

    private void enable() {
        ConsoleUtils.startMessage();

        filesManager.createFiles();

        registerCommand.register();

        registerEvents.register();

        registerPlaceHolder.register();

        serviceManager.startServices();

        registerMetrics.register();
    }

    private void verifyCompabilityPlugins() {
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");
    }

    @Override
    public void onDisable() {
        serviceManager.stopServices();
    }

}