package me.needkg.daynightpvp.command.manager;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.command.DayNightPvpCommand;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.service.plugin.PluginService;
import me.needkg.daynightpvp.utils.logging.Logger;

public class CommandManager {

    private final MessageManager messageManager;
    private final PluginService pluginService;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final ConfigurationFile configurationFile;

    public CommandManager(MessageManager messageManager, PluginService pluginService, GlobalConfigurationManager globalConfigurationManager, ConfigurationFile configurationFile) {
        this.messageManager = messageManager;
        this.pluginService = pluginService;
        this.globalConfigurationManager = globalConfigurationManager;
        this.configurationFile = configurationFile;
    }

    public void register() {
        DayNightPvpCommand dayNightPvPCommand = new DayNightPvpCommand(messageManager, pluginService, globalConfigurationManager, configurationFile);

        Logger.verbose("Registering DayNightPvP command...");
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dayNightPvPCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dayNightPvPCommand);
    }
} 