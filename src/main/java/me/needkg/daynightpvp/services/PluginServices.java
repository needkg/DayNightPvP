package me.needkg.daynightpvp.services;

import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.LangManager;
import me.needkg.daynightpvp.listeners.ListenersHandler;
import me.needkg.daynightpvp.placeholder.PlaceholderHandler;
import me.needkg.daynightpvp.runnables.RunnableHandler;

public class PluginServices {

    private final ConfigManager configManager;
    private final LangManager langManager;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;

    public PluginServices(ConfigManager configManager, LangManager langManager, RunnableHandler runnableHandler, ListenersHandler listenersHandler, PlaceholderHandler placeholderHandler) {
        this.configManager = configManager;
        this.langManager = langManager;
        this.runnableHandler = runnableHandler;
        this.listenersHandler = listenersHandler;
        this.placeholderHandler = placeholderHandler;
    }

    public void reloadPlugin() {
        reloadFiles();
        runnableHandler.restart();
        listenersHandler.restart();
        placeholderHandler.restart();
    }

    public void reloadFiles() {
        configManager.refreshFile();
        langManager.refreshFile();
    }

}
