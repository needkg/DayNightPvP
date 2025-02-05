package me.needkg.daynightpvp.core;

import me.needkg.daynightpvp.command.manager.CommandManager;
import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.access.LanguageAccess;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.file.LanguageFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.integration.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.integration.vault.LoseMoney;
import me.needkg.daynightpvp.listener.manager.ListenerManager;
import me.needkg.daynightpvp.metric.MetricsManager;
import me.needkg.daynightpvp.service.plugin.PluginService;
import me.needkg.daynightpvp.service.update.UpdateService;
import me.needkg.daynightpvp.tasks.manager.TaskManager;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;

public class DependencyContainer {
    private static DependencyContainer instance;

    private ConfigurationFile configurationFile;
    private ConfigurationAccess configurationAccess;
    private GlobalConfigurationManager globalConfigurationManager;
    private WorldConfigurationManager worldConfigurationManager;

    private LanguageFile languageFile;
    private LanguageAccess languageAccess;
    private MessageManager messageManager;

    private UpdateService updateService;
    private TaskManager taskManager;
    private LoseMoney loseMoney;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private PlaceholderManager placeholderManager;
    private GriefPreventionManager griefPreventionManager;
    private MetricsManager metricsManager;
    private PluginService pluginService;
    private WorldStateManager worldStateManager;
    private DependencyContainer() {
        initializeDependencies();
    }

    public static void initializeContainer() {
        instance = new DependencyContainer();
    }

    public static DependencyContainer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DependencyContainer is not initialized!");
        }
        return instance;
    }

    private void initializeDependencies() {
        // Configuração Principal
        configurationFile = new ConfigurationFile();
        configurationAccess = new ConfigurationAccess(configurationFile);
        globalConfigurationManager = new GlobalConfigurationManager(configurationAccess);
        worldConfigurationManager = new WorldConfigurationManager(configurationAccess);

        // Configuração de Linguagem
        languageFile = new LanguageFile(globalConfigurationManager);
        languageAccess = new LanguageAccess(languageFile);
        messageManager = new MessageManager(languageAccess);

        // Outros Serviços
        worldStateManager = new WorldStateManager(globalConfigurationManager, worldConfigurationManager);

        taskManager = new TaskManager(globalConfigurationManager, worldConfigurationManager, messageManager, worldStateManager);

        loseMoney = new LoseMoney(worldConfigurationManager, messageManager, worldStateManager);

        updateService = new UpdateService(messageManager);

        listenerManager = new ListenerManager(messageManager, griefPreventionManager, globalConfigurationManager, updateService, worldConfigurationManager, loseMoney, taskManager, worldStateManager);

        placeholderManager = new PlaceholderManager(messageManager, globalConfigurationManager, worldStateManager);

        pluginService = new PluginService(configurationFile, languageFile, taskManager, listenerManager, placeholderManager, worldStateManager);

        griefPreventionManager = new GriefPreventionManager();

        commandManager = new CommandManager(messageManager, pluginService, globalConfigurationManager, configurationFile);

        metricsManager = new MetricsManager();
    }

    public ConfigurationFile getConfigurationFile() {
        return configurationFile;
    }

    public GlobalConfigurationManager getGlobalConfigurationManager() {
        return globalConfigurationManager;
    }

    public WorldConfigurationManager getWorldConfigurationManager() {
        return worldConfigurationManager;
    }

    public LanguageFile getLanguageFile() {
        return languageFile;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public WorldStateManager getWorldStateManager() {
        return worldStateManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public PlaceholderManager getPlaceholderHandler() {
        return placeholderManager;
    }

    public MetricsManager getMetricsManager() {
        return metricsManager;
    }
} 