package me.needkg.daynightpvp.di;

import me.needkg.daynightpvp.commands.CommandHandler;
import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.LangManager;
import me.needkg.daynightpvp.config.settings.GeneralSettings;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.config.validator.ConfigValidator;
import me.needkg.daynightpvp.config.validator.LangValidator;
import me.needkg.daynightpvp.listeners.ListenersHandler;
import me.needkg.daynightpvp.metrics.MetricsHandler;
import me.needkg.daynightpvp.placeholder.PlaceholderHandler;
import me.needkg.daynightpvp.runnables.RunnableHandler;
import me.needkg.daynightpvp.services.PluginServices;
import me.needkg.daynightpvp.vault.LoseMoneyOnDeath;

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
    private MetricsHandler metricsHandler;
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
        worldSettings = new WorldSettings(configValidator);

        langManager = new LangManager(generalSettings);
        langValidator = new LangValidator(langManager);
        messageSettings = new MessageSettings(langValidator);

        runnableHandler = new RunnableHandler(worldSettings);

        loseMoneyOnDeath = new LoseMoneyOnDeath(worldSettings, messageSettings);

        listenersHandler = new ListenersHandler(generalSettings);

        placeholderHandler = new PlaceholderHandler();

        pluginServices = new PluginServices(configManager, langManager, runnableHandler, listenersHandler, placeholderHandler);

        commandHandler = new CommandHandler();
        metricsHandler = new MetricsHandler();
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

    public MetricsHandler getBStatsHandler() {
        return metricsHandler;
    }

    public PluginServices getPluginServices() {
        return pluginServices;
    }
} 