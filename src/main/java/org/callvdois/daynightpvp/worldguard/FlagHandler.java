package org.callvdois.daynightpvp.worldguard;

import org.callvdois.daynightpvp.DayNightPvP;

public class FlagHandler {

    public void register() {
        if (DayNightPvP.worldGuardIsPresent) {
            AllowDaytimePvpFlag.register();
        }
    }

}
