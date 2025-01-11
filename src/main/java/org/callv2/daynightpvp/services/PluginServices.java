package org.callv2.daynightpvp.services;

import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.listeners.ListenersHandler;
import org.callv2.daynightpvp.placeholder.PlaceholderHandler;
import org.callv2.daynightpvp.runnables.RunnableHandler;

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
