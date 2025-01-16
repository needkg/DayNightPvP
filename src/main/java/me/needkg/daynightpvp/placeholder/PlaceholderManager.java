package me.needkg.daynightpvp.placeholder;

import me.needkg.daynightpvp.DayNightPvP;

public class PlaceholderManager {

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStatePlaceholder().register();
        }
    }

    public void unregister() {
        if (DayNightPvP.isPlaceholderPresent) {
            new WorldStatePlaceholder().unregister();
        }
    }

    public void restart() {
        unregister();
        register();
    }
}
