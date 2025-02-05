package me.needkg.daynightpvp.integration.placeholder;

import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.integration.placeholder.providers.WorldStateProvider;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.logging.Logger;
import me.needkg.daynightpvp.utils.plugin.PluginValidator;

public class PlaceholderManager {

    private final WorldStateProvider worldStateProvider;

    public PlaceholderManager(MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager, WorldStateManager worldStateManager) {
        this.worldStateProvider = new WorldStateProvider(messageManager, globalConfigurationManager, worldStateManager);
    }

    public void register() {
        if (PluginValidator.isPlaceholderPresent()) {
            Logger.verbose("Registering WorldStateProvider...");
            worldStateProvider.register();
        } else {
            Logger.debug("PlaceholderAPI is not installed, skipping registration...");
        }
    }

    public void unregister() {
        if (PluginValidator.isPlaceholderPresent()) {
            worldStateProvider.unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
