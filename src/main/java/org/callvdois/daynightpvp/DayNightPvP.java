package org.callvdois.daynightpvp;

import org.callvdois.daynightpvp.commands.RegisterCommands;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.events.RegisterEvents;
import org.callvdois.daynightpvp.metrics.Metrics;
import org.callvdois.daynightpvp.placeholder.RegisterPlaceHolder;
import org.callvdois.daynightpvp.service.DnpService;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PluginUtils;
import org.callvdois.daynightpvp.worldguard.RegisterCustomFlag;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightPvP extends JavaPlugin {

    private static DayNightPvP instance;

    private final FilesManager filesManager;
    private final DnpService dnpService;
    private final RegisterCommands registerCommands;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final RegisterCustomFlag registerCustomFlag;

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;

    public DayNightPvP() {
        instance = this;

        filesManager = new FilesManager();
        dnpService = new DnpService();
        registerCommands = new RegisterCommands();
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        registerCustomFlag = new RegisterCustomFlag();
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

        registerMetrics();
    }

    public void load() {

        verifyCompabilityPlugins();

        if (worldGuardIsPresent) {
            registerCustomFlag.run();
        }
    }

    public void enable() {
        ConsoleUtils.startMessage();

        filesManager.createFiles();
        filesManager.verifyConfigVersion();
        filesManager.verfiyLangsVersion();

        ConfigManager.updateConfigs();
        LangManager.updateLangs();

        dnpService.startService();

        registerCommands.register();

        registerEvents.register();

        registerPlaceHolder.register();
    }

    private void registerMetrics() {
        new Metrics(this, 19067);
    }

    private void verifyCompabilityPlugins() {
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");

    }

}