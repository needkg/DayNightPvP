package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.DayNightPvP;

public class PlaceholderHandler {

    public void register() {
        if (DayNightPvP.placeHolderIsPresent) {
            new PvpStatusPlaceholder().register();
        }
    }

    public void unregister() {
        if (DayNightPvP.placeHolderIsPresent) {
            new PvpStatusPlaceholder().unregister();
        }
    }
}
