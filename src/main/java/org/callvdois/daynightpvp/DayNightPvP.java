package org.callvdois.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.callvdois.daynightpvp.commands.RegisterCommand;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.events.RegisterEvents;
import org.callvdois.daynightpvp.metrics.RegisterMetrics;
import org.callvdois.daynightpvp.placeholder.RegisterPlaceHolder;
import org.callvdois.daynightpvp.service.TimeChecker;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PluginUtils;
import org.callvdois.daynightpvp.worldguard.RegisterCustomFlag;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;
    private static DayNightPvP instance;
    private final FilesManager filesManager;
    private final TimeChecker timeChecker;
    private final RegisterCommand registerCommand;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final RegisterCustomFlag registerCustomFlag;
    private final RegisterMetrics registerMetrics;

    public DayNightPvP() {
        instance = this;

        filesManager = new FilesManager();
        timeChecker = new TimeChecker();
        registerCommand = new RegisterCommand();
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        registerCustomFlag = new RegisterCustomFlag();
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
        registerMetrics.register();
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
        filesManager.verifyConfigVersion();
        filesManager.verfiyLangsVersion();

        ConfigManager.updateConfigs();
        LangManager.updateLangs();

        timeChecker.run();

        registerCommand.register();

        registerEvents.register();

        registerPlaceHolder.register();
    }

    //public void restart() {
    //}

    private void verifyCompabilityPlugins() {
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");

    }

}