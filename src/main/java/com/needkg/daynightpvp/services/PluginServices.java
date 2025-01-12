package com.needkg.daynightpvp.services;

import com.needkg.daynightpvp.files.ConfigFile;
import com.needkg.daynightpvp.files.LangFile;
import com.needkg.daynightpvp.listeners.ListenersHandler;
import com.needkg.daynightpvp.placeholder.PlaceholderHandler;
import com.needkg.daynightpvp.runnables.RunnableHandler;

public class PluginServices {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;

    public PluginServices(ConfigFile configFile, LangFile langFile, RunnableHandler runnableHandler, ListenersHandler listenersHandler, PlaceholderHandler placeholderHandler) {
        this.configFile = configFile;
        this.langFile = langFile;
        this.runnableHandler = runnableHandler;
        this.listenersHandler = listenersHandler;
        this.placeholderHandler = placeholderHandler;
    }

    public void reloadPlugin() {
        reloadFiles();
        restartListeners();
        restartPlaceholders();
        restartRunnables();
    }

    private void reloadFiles() {
        configFile.createFile();
        langFile.createFile();
    }

    private void restartListeners() {
        listenersHandler.unregisterAll();
        listenersHandler.register();
    }

    private void restartPlaceholders() {
        placeholderHandler.unregister();
        placeholderHandler.register();
    }

    private void restartRunnables() {
        runnableHandler.stopAllRunnables();
        runnableHandler.startAllRunnables();
    }
}
