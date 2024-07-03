package org.callvdois.daynightpvp.worldguard;

import org.callvdois.daynightpvp.DayNightPvP;

public class WorldGuardManager {

    public void register() {
        if (DayNightPvP.worldGuardIsPresent) {
            AllowPvpOnDayFlag.register();
        }
    }

}
