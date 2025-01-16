package me.needkg.daynightpvp.services;

import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.LangManager;
import me.needkg.daynightpvp.listeners.ListenersManager;
import me.needkg.daynightpvp.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.runnables.RunnableManager;

public class PluginServices {

    private final ConfigManager configManager;
    private final LangManager langManager;
    private final ListenersManager listenersManager;
    private final PlaceholderManager placeholderManager;
    private final RunnableManager runnableManager;

    public PluginServices(ConfigManager configManager, LangManager langManager, RunnableManager runnableManager, ListenersManager listenersManager, PlaceholderManager placeholderManager) {
        this.configManager = configManager;
        this.langManager = langManager;
        this.runnableManager = runnableManager;
        this.listenersManager = listenersManager;
        this.placeholderManager = placeholderManager;
    }

    public void reloadPlugin() {
        reloadFiles();
        runnableManager.restart();
        listenersManager.restart();
        placeholderManager.restart();
    }

    public void reloadFiles() {
        configManager.refreshFile();
        langManager.refreshFile();
    }

}
