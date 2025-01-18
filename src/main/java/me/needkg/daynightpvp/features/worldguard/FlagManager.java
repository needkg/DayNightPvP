package me.needkg.daynightpvp.features.worldguard;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.features.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.utils.LoggingUtil;

public class FlagManager {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            LoggingUtil.sendVerboseMessage("Registering DaytimePvpFlag (WorldGuard)...");
            DaytimePvpFlag.register();
        }
    }

}
