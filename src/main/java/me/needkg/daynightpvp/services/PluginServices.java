package me.needkg.daynightpvp.services;

import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.listeners.ListenersHandler;
import me.needkg.daynightpvp.placeholder.PlaceholderHandler;
import me.needkg.daynightpvp.runnables.RunnableHandler;

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

    public void reloadFiles() {
        dependencyContainer.getConfigManager().initializeFile();
        dependencyContainer.getLangManager().initializeFile();
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
