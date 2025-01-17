package me.needkg.daynightpvp.core.di;

import me.needkg.daynightpvp.api.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.commands.CommandManager;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.LanguageManager;
import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;
import me.needkg.daynightpvp.configuration.validators.LanguageValidator;
import me.needkg.daynightpvp.features.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.features.vault.LoseMoney;
import me.needkg.daynightpvp.listeners.ListenerManager;
import me.needkg.daynightpvp.metrics.MetricsManager;
import me.needkg.daynightpvp.services.PluginService;
import me.needkg.daynightpvp.tasks.TaskManager;

public class DependencyContainer {
    private static DependencyContainer instance;

    private ConfigurationManager configurationManager;
    private ConfigurationValidator configurationValidator;
    private ConfigurationContainer configurationContainer;

    private LanguageManager languageManager;
    private LanguageValidator languageValidator;
    private MessageContainer messageContainer;

    private TaskManager taskManager;
    private LoseMoney loseMoney;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private PlaceholderManager placeholderManager;
    private GriefPreventionManager griefPreventionManager;
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
        configurationContainer = new ConfigurationContainer(configurationValidator);

        languageManager = new LanguageManager(configurationContainer);
        languageValidator = new LanguageValidator(languageManager);
        messageContainer = new MessageContainer(languageValidator);

        taskManager = new TaskManager(configurationContainer);

        loseMoney = new LoseMoney(configurationContainer, messageContainer);

        listenerManager = new ListenerManager(configurationContainer);

        placeholderManager = new PlaceholderManager();

        pluginService = new PluginService(configurationManager, languageManager, taskManager, listenerManager, placeholderManager);

        griefPreventionManager = new GriefPreventionManager();
        commandManager = new CommandManager();
        metricsManager = new MetricsManager();
    }

    public ConfigurationManager getConfigManager() {
        return configurationManager;
    }

    public ConfigurationContainer getConfigurationContainer() {
        return configurationContainer;
    }

    public LanguageManager getLangManager() {
        return languageManager;
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
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

    public GriefPreventionManager getGriefPreventionManager() {
        return griefPreventionManager;
    }

    public MetricsManager getBStatsHandler() {
        return metricsManager;
    }

    public PluginService getPluginServices() {
        return pluginService;
    }
} 