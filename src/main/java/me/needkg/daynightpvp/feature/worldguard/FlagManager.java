package me.needkg.daynightpvp.feature.worldguard;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.feature.worldguard.flags.DaytimePvpFlag;

public class FlagManager {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            DaytimePvpFlag.register();
        }
    }

}
