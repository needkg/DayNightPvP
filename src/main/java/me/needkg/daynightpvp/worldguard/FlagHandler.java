package me.needkg.daynightpvp.worldguard;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.worldguard.flags.AllowDaytimePvpFlag;

public class FlagHandler {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            AllowDaytimePvpFlag.register();
        }
    }

}
