package org.callvdois.daynightpvp.placeholder;

import org.callvdois.daynightpvp.DayNightPvP;

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
