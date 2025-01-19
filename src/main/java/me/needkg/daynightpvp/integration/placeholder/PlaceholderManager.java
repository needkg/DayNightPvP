package me.needkg.daynightpvp.integration.placeholder;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.integration.placeholder.providers.WorldStateProvider;
import me.needkg.daynightpvp.util.logging.Logger;

public class PlaceholderManager {

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            Logger.verbose("Registering WorldStateProvider...");
            new WorldStateProvider().register();
        } else {
            Logger.debug("PlaceholderAPI is not installed, skipping registration...");
        }
    }

    public void unregister() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStateProvider().unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
