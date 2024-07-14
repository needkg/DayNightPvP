package org.callv2.daynightpvp.services;

import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.listeners.ListenersHandler;
import org.callv2.daynightpvp.placeholder.PlaceholderHandler;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;


public class PluginServices {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;

    public PluginServices(LoseMoneyOnDeath loseMoneyOnDeath, RunnableHandler runnableHandler) {
        configFile = new ConfigFile();
        langFile = new LangFile(configFile);
        this.runnableHandler = runnableHandler;
        listenersHandler = new ListenersHandler(configFile, langFile, loseMoneyOnDeath);
        placeholderHandler = new PlaceholderHandler(langFile, configFile);
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
