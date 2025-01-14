package me.needkg.daynightpvp.placeholder;

import me.needkg.daynightpvp.DayNightPvP;

public class PlaceholderHandler {

    public void register() {
        if (DayNightPvP.isPlaceholderPresent) {
            new PvpStatusPlaceholder().register();
        }
    }

    public void unregister() {
        if (DayNightPvP.isPlaceholderPresent) {
            new PvpStatusPlaceholder().unregister();
        }
    }
}
