package com.needkg.daynightpvp.di;

import com.needkg.daynightpvp.bstats.BStatsHandler;
import com.needkg.daynightpvp.commands.CommandHandler;
import com.needkg.daynightpvp.files.ConfigFile;
import com.needkg.daynightpvp.files.LangFile;
import com.needkg.daynightpvp.listeners.ListenersHandler;
import com.needkg.daynightpvp.placeholder.PlaceholderHandler;
import com.needkg.daynightpvp.runnables.RunnableHandler;
import com.needkg.daynightpvp.services.PluginServices;
import com.needkg.daynightpvp.vault.LoseMoneyOnDeath;

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