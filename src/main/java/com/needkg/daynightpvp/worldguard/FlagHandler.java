package com.needkg.daynightpvp.worldguard;

import com.needkg.daynightpvp.DayNightPvP;

public class FlagHandler {

    public static void register() {
        if (DayNightPvP.worldGuardIsPresent) {
            AllowDaytimePvpFlag.register();
        }
    }

}
