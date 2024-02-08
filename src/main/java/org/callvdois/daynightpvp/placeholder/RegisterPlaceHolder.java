package org.callvdois.daynightpvp.placeholder;

import org.callvdois.daynightpvp.DayNightPvP;

public class RegisterPlaceHolder {

    public void register() {
        if (DayNightPvP.placeHolderIsPresent) {
            PvpStatus pvpStatus = new PvpStatus();
            pvpStatus.unregister();
            pvpStatus.register();
        }
    }

}
