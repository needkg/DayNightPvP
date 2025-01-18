package me.needkg.daynightpvp.features.worldguard;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.features.worldguard.flags.DaytimePvpFlag;

public class FlagManager {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            DaytimePvpFlag.register();
        }
    }

}
