package com.needkg.daynightpvp.di;

import com.needkg.daynightpvp.bstats.BStatsHandler;
import com.needkg.daynightpvp.commands.CommandHandler;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.settings.GeneralSettings;
import com.needkg.daynightpvp.config.settings.MessageSettings;
import com.needkg.daynightpvp.config.settings.WorldSettings;
import com.needkg.daynightpvp.config.validator.ConfigValidator;
import com.needkg.daynightpvp.config.validator.LangValidator;
import com.needkg.daynightpvp.listeners.ListenersHandler;
import com.needkg.daynightpvp.placeholder.PlaceholderHandler;
import com.needkg.daynightpvp.runnables.RunnableHandler;
import com.needkg.daynightpvp.services.PluginServices;
import com.needkg.daynightpvp.vault.LoseMoneyOnDeath;

public class DependencyContainer {
    private static DependencyContainer instance;

    private ConfigManager configManager;
    private ConfigValidator configValidator;
    private GeneralSettings generalSettings;
    private WorldSettings worldSettings;

    private LangManager langManager;
    private LangValidator langValidator;
    private MessageSettings messageSettings;

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

        configManager = new ConfigManager();
        configValidator = new ConfigValidator(configManager);
        generalSettings = new GeneralSettings(configValidator);
        worldSettings = new WorldSettings(configManager, configValidator);

        langManager = new LangManager(generalSettings);
        langValidator = new LangValidator(langManager);
        messageSettings = new MessageSettings(langValidator);

        runnableHandler = new RunnableHandler(worldSettings);

        loseMoneyOnDeath = new LoseMoneyOnDeath(worldSettings, messageSettings);

        listenersHandler = new ListenersHandler(generalSettings);

        placeholderHandler = new PlaceholderHandler();

        pluginServices = new PluginServices(this, runnableHandler, listenersHandler, placeholderHandler);

        commandHandler = new CommandHandler();
        bStatsHandler = new BStatsHandler();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ConfigValidator getConfigValidator() {
        return configValidator;
    }

    public GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    public WorldSettings getWorldSettings() {
        return worldSettings;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public LangValidator getLangValidator() {
        return langValidator;
    }

    public MessageSettings getMessageSettings() {
        return messageSettings;
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