package me.needkg.daynightpvp.di;

import me.needkg.daynightpvp.commands.CommandManager;
import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.LangManager;
import me.needkg.daynightpvp.config.settings.GeneralSettings;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.config.validator.ConfigValidator;
import me.needkg.daynightpvp.config.validator.LangValidator;
import me.needkg.daynightpvp.listeners.ListenersManager;
import me.needkg.daynightpvp.metrics.MetricsManager;
import me.needkg.daynightpvp.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.runnables.RunnableManager;
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

    private RunnableManager runnableManager;
    private LoseMoneyOnDeath loseMoneyOnDeath;
    private CommandManager commandManager;
    private ListenersManager listenersManager;
    private PlaceholderManager placeholderManager;
    private MetricsManager metricsManager;
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

        runnableManager = new RunnableManager(worldSettings);

        loseMoneyOnDeath = new LoseMoneyOnDeath(worldSettings, messageSettings);

        listenersManager = new ListenersManager(generalSettings);

        placeholderManager = new PlaceholderManager();

        pluginServices = new PluginServices(configManager, langManager, runnableManager, listenersManager, placeholderManager);

        commandManager = new CommandManager();
        metricsManager = new MetricsManager();
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

    public RunnableManager getRunnableHandler() {
        return runnableManager;
    }

    public LoseMoneyOnDeath getLoseMoneyOnDeath() {
        return loseMoneyOnDeath;
    }

    public CommandManager getCommandHandler() {
        return commandManager;
    }

    public ListenersManager getListenersHandler() {
        return listenersManager;
    }

    public PlaceholderManager getPlaceholderHandler() {
        return placeholderManager;
    }

    public MetricsManager getBStatsHandler() {
        return metricsManager;
    }

    public PluginServices getPluginServices() {
        return pluginServices;
    }
} 