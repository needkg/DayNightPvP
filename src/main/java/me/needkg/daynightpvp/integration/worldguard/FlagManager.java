package me.needkg.daynightpvp.integration.worldguard;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.integration.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.util.logging.Logger;

public class FlagManager {

    public static void register() {
        if (DayNightPvP.isWorldGuardPresent) {
            Logger.verbose("Registering DaytimePvpFlag (WorldGuard)...");
            DaytimePvpFlag.register();
        }
    }

}
