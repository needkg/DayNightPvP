package com.needkg.daynightpvp.services;

import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.listeners.ListenersHandler;
import com.needkg.daynightpvp.placeholder.PlaceholderHandler;
import com.needkg.daynightpvp.runnables.RunnableHandler;

public class PluginServices {

    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;
    private final DependencyContainer dependencyContainer;

    public PluginServices(DependencyContainer dependencyContainer, RunnableHandler runnableHandler, ListenersHandler listenersHandler, PlaceholderHandler placeholderHandler) {
        this.dependencyContainer = dependencyContainer;
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
        dependencyContainer.getConfigManager().createFile();
        dependencyContainer.getLangManager().createFile();
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
