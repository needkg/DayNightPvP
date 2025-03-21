package me.needkg.daynightpvp.integration.placeholder;

import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.integration.placeholder.providers.WorldStateProvider;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.logging.Logger;
import me.needkg.daynightpvp.utils.plugin.PluginValidator;

public class PlaceholderManager {

    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final WorldStateManager worldStateManager;


    public PlaceholderManager(MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager, WorldStateManager worldStateManager) {
        this.messageManager = messageManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.worldStateManager = worldStateManager;
    }

    public void register() {
        if (PluginValidator.isPlaceholderPresent()) {
            Logger.verbose("Registering WorldStateProvider...");
            new WorldStateProvider(messageManager, globalConfigurationManager, worldStateManager).register();
        } else {
            Logger.debug("PlaceholderAPI is not installed, skipping registration...");
        }
    }

    public void unregister() {
        if (PluginValidator.isPlaceholderPresent()) {
            new WorldStateProvider(messageManager, globalConfigurationManager, worldStateManager).unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
