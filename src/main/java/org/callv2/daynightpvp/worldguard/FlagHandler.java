package org.callv2.daynightpvp.worldguard;

import org.callv2.daynightpvp.DayNightPvP;

public class FlagHandler {

    public static void register() {
        if (DayNightPvP.worldGuardIsPresent) {
            AllowDaytimePvpFlag.register();
        }
    }

}
