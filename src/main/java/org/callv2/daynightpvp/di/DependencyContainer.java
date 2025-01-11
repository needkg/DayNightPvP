package org.callv2.daynightpvp.di;

import org.callv2.daynightpvp.bstats.BStatsHandler;
import org.callv2.daynightpvp.commands.CommandHandler;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.listeners.ListenersHandler;
import org.callv2.daynightpvp.placeholder.PlaceholderHandler;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.services.PluginServices;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;

public class DependencyContainer {
    private static DependencyContainer instance;

    private ConfigFile configFile;
    private LangFile langFile;
    private RunnableHandler runnableHandler;
    private LoseMoneyOnDeath loseMoneyOnDeath;
    private CommandHandler commandHandler;
    private ListenersHandler listenersHandler;
    private PlaceholderHandler placeholderHandler;
    private BStatsHandler bStatsHandler;
    private PluginServices pluginServices;

    private DependencyContainer() {
        initializeDependencies();
    }

    public static void initialize() {
        instance = new DependencyContainer();
    }

    public static DependencyContainer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DependencyContainer is not initialized!");
        }
        return instance;
    }

    private void initializeDependencies() {
        configFile = new ConfigFile();
        langFile = new LangFile(configFile);
        runnableHandler = new RunnableHandler(configFile, langFile);
        loseMoneyOnDeath = new LoseMoneyOnDeath(configFile, langFile);
        commandHandler = new CommandHandler();
        listenersHandler = new ListenersHandler(configFile);
        placeholderHandler = new PlaceholderHandler(configFile, langFile);
        pluginServices = new PluginServices(configFile, langFile, runnableHandler, listenersHandler, placeholderHandler);
        bStatsHandler = new BStatsHandler();
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public LangFile getLangFile() {
        return langFile;
    }

    public RunnableHandler getRunnableHandler() {
        return runnableHandler;
    }

    public LoseMoneyOnDeath getLoseMoneyOnDeath() {
        return loseMoneyOnDeath;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public ListenersHandler getListenersHandler() {
        return listenersHandler;
    }

    public PlaceholderHandler getPlaceholderHandler() {
        return placeholderHandler;
    }

    public BStatsHandler getBStatsHandler() {
        return bStatsHandler;
    }

    public PluginServices getPluginServices() {
        return pluginServices;
    }
} 