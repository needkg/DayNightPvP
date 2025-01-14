package me.needkg.daynightpvp.worldguard;

import me.needkg.daynightpvp.DayNightPvP;

public class FlagHandler {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            AllowDaytimePvpFlag.register();
        }
    }

}
