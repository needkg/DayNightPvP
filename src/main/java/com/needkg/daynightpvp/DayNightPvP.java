package com.needkg.daynightpvp;

import com.needkg.daynightpvp.commands.RegisterCommands;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.StartupFiles;
import com.needkg.daynightpvp.events.RegisterEvents;
import com.needkg.daynightpvp.metrics.Metrics;
import com.needkg.daynightpvp.placeholder.RegisterPlaceHolder;
import com.needkg.daynightpvp.service.DnpService;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class DayNightPvP extends JavaPlugin {

    public static JavaPlugin plugin;
    private ConsoleUtils consoleUtils;

    @Override
    public void onEnable() {
        plugin = this;

        consoleUtils = new ConsoleUtils();
        consoleUtils.sendMessage(consoleUtils.STARTUP_MESSAGE);

        StartupFiles startupFiles = new StartupFiles();
        startupFiles.startConfigFile(this);
        startupFiles.startLangsFile(this);

        FilesManager filesManager = new FilesManager();
        filesManager.verifyConfigVersion(this);
        filesManager.verfiyLangsVersion(this);

        ConfigManager configManager = new ConfigManager();
        configManager.updateConfigs();

        LangManager langManager = new LangManager();
        langManager.updateLangs(this);

        DnpService dnpService = new DnpService();
        dnpService.startService();

        RegisterCommands registerCommands = new RegisterCommands();
        registerCommands.register(this);

        RegisterEvents registerEvents = new RegisterEvents();
        registerEvents.register();

        RegisterPlaceHolder registerPlaceHolder = new RegisterPlaceHolder();
        registerPlaceHolder.register();

        new Metrics(this, 19067);

        consoleUtils.sendMessage(consoleUtils.STARTUP_END_MESSAGE);
    }

    @Override
    public void onDisable() {
        consoleUtils.sendMessage(consoleUtils.SHUTDOWN_MESSAGE);
    }

}