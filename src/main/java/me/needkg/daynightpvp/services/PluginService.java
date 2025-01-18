package me.needkg.daynightpvp.services;

import me.needkg.daynightpvp.api.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.LanguageManager;
import me.needkg.daynightpvp.listeners.ListenerManager;
import me.needkg.daynightpvp.tasks.TaskManager;
import me.needkg.daynightpvp.utils.LoggingUtil;

public class PluginService {

    private final ConfigurationManager configurationManager;
    private final LanguageManager languageManager;
    private final ListenerManager listenerManager;
    private final PlaceholderManager placeholderManager;
    private final TaskManager taskManager;

    public PluginService(ConfigurationManager configurationManager, LanguageManager languageManager, TaskManager taskManager, ListenerManager listenerManager, PlaceholderManager placeholderManager) {
        this.configurationManager = configurationManager;
        this.languageManager = languageManager;
        this.taskManager = taskManager;
        this.listenerManager = listenerManager;
        this.placeholderManager = placeholderManager;
    }

    public void reloadPlugin() {
        LoggingUtil.sendDebugMessage("Reloading files...");
        reloadFiles();
        LoggingUtil.sendDebugMessage("Restarting scheduled tasks...");
        taskManager.restart();
        LoggingUtil.sendDebugMessage("Restarting event handlers...");
        listenerManager.restart();
        LoggingUtil.sendDebugMessage("Restarting PlaceholderAPI...");
        placeholderManager.restart();
    }

    public void reloadFiles() {
        configurationManager.refreshFile();
        languageManager.refreshFile();
    }

}
