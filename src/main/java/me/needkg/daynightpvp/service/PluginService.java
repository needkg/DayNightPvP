package me.needkg.daynightpvp.service;

import me.needkg.daynightpvp.api.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.LanguageManager;
import me.needkg.daynightpvp.listeners.ListenerManager;
import me.needkg.daynightpvp.task.TaskManager;

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
        reloadFiles();
        taskManager.restart();
        listenerManager.restart();
        placeholderManager.restart();
    }

    public void reloadFiles() {
        configurationManager.refreshFile();
        languageManager.refreshFile();
    }

}
