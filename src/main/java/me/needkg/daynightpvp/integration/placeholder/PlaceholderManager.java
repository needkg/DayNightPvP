package me.needkg.daynightpvp.integration.placeholder;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.integration.placeholder.providers.WorldStateProvider;
import me.needkg.daynightpvp.utis.logging.Logger;

public class PlaceholderManager {

    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;

    public PlaceholderManager(MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager) {
        this.messageManager = messageManager;
        this.globalConfigurationManager = globalConfigurationManager;
    }

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            Logger.verbose("Registering WorldStateProvider...");
            new WorldStateProvider(messageManager, globalConfigurationManager).register();
        } else {
            Logger.debug("PlaceholderAPI is not installed, skipping registration...");
        }
    }

    public void unregister() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStateProvider(messageManager, globalConfigurationManager).unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
