package me.needkg.daynightpvp.api.placeholder;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.api.placeholder.providers.WorldStateProvider;

public class PlaceholderManager {

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStateProvider().register();
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
