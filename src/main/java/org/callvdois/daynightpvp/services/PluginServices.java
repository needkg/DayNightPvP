package org.callvdois.daynightpvp.services;

import org.callvdois.daynightpvp.Listeners.ListenersHandler;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.placeholder.PlaceholderHandler;
import org.callvdois.daynightpvp.runnables.RunnableHandler;


public class PluginServices {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private final ListenersHandler listenersHandler;
    private final PlaceholderHandler placeholderHandler;
    private final RunnableHandler runnableHandler;

    public PluginServices() {
        configFile = new ConfigFile();
        langFile = new LangFile();
        listenersHandler = new ListenersHandler();
        placeholderHandler = new PlaceholderHandler();
        runnableHandler = new RunnableHandler();
    }

    public void reloadPlugin() {
        configFile.createFile();
        langFile.createFile();
        listenersHandler.unregisterAll();
        listenersHandler.register();
        placeholderHandler.unregister();
        placeholderHandler.register();
        runnableHandler.stopAllRunnables();
        runnableHandler.startAllRunnables();
    }

}
