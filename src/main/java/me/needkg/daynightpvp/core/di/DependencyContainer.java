package me.needkg.daynightpvp.core.di;

import me.needkg.daynightpvp.api.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.command.CommandManager;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.LanguageManager;
import me.needkg.daynightpvp.configuration.settings.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;
import me.needkg.daynightpvp.configuration.validator.LanguageValidator;
import me.needkg.daynightpvp.feature.vault.LoseMoney;
import me.needkg.daynightpvp.listeners.ListenerManager;
import me.needkg.daynightpvp.metrics.MetricsManager;
import me.needkg.daynightpvp.service.PluginService;
import me.needkg.daynightpvp.task.TaskManager;

public class DependencyContainer {
    private static DependencyContainer instance;

    private ConfigurationManager configurationManager;
    private ConfigurationValidator configurationValidator;
    private GeneralConfiguration generalConfiguration;
    private WorldConfiguration worldConfiguration;

    private LanguageManager languageManager;
    private LanguageValidator languageValidator;
    private MessageConfiguration messageConfiguration;

    private TaskManager taskManager;
    private LoseMoney loseMoney;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private PlaceholderManager placeholderManager;
    private MetricsManager metricsManager;
    private PluginService pluginService;

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

        configurationManager = new ConfigurationManager();
        configurationValidator = new ConfigurationValidator(configurationManager);
        generalConfiguration = new GeneralConfiguration(configurationValidator);
        worldConfiguration = new WorldConfiguration(configurationValidator);

        languageManager = new LanguageManager(generalConfiguration);
        languageValidator = new LanguageValidator(languageManager);
        messageConfiguration = new MessageConfiguration(languageValidator);

        taskManager = new TaskManager(worldConfiguration);

        loseMoney = new LoseMoney(worldConfiguration, messageConfiguration);

        listenerManager = new ListenerManager(generalConfiguration);

        placeholderManager = new PlaceholderManager();

        pluginService = new PluginService(configurationManager, languageManager, taskManager, listenerManager, placeholderManager);

        commandManager = new CommandManager();
        metricsManager = new MetricsManager();
    }

    public ConfigurationManager getConfigManager() {
        return configurationManager;
    }

    public GeneralConfiguration getGeneralSettings() {
        return generalConfiguration;
    }

    public WorldConfiguration getWorldSettings() {
        return worldConfiguration;
    }

    public LanguageManager getLangManager() {
        return languageManager;
    }

    public MessageConfiguration getMessageSettings() {
        return messageConfiguration;
    }

    public TaskManager getRunnableHandler() {
        return taskManager;
    }

    public LoseMoney getLoseMoneyOnDeath() {
        return loseMoney;
    }

    public CommandManager getCommandHandler() {
        return commandManager;
    }

    public ListenerManager getListenersHandler() {
        return listenerManager;
    }

    public PlaceholderManager getPlaceholderHandler() {
        return placeholderManager;
    }

    public MetricsManager getBStatsHandler() {
        return metricsManager;
    }

    public PluginService getPluginServices() {
        return pluginService;
    }
} 