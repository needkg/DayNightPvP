package me.needkg.daynightpvp.services;

import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.file.LanguageFile;
import me.needkg.daynightpvp.features.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.listeners.ListenerManager;
import me.needkg.daynightpvp.tasks.TaskManager;
import me.needkg.daynightpvp.utils.LoggingUtil;

public class PluginService {

    private final ConfigurationFile configurationFile;
    private final LanguageFile languageFile;
    private final ListenerManager listenerManager;
    private final PlaceholderManager placeholderManager;
    private final TaskManager taskManager;

    public PluginService(ConfigurationFile configurationFile, LanguageFile languageFile, TaskManager taskManager, ListenerManager listenerManager, PlaceholderManager placeholderManager) {
        this.configurationFile = configurationFile;
        this.languageFile = languageFile;
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
        configurationFile.refreshFile();
        languageFile.refreshFile();
    }

}
