package me.needkg.daynightpvp.integration.worldguard;

import me.needkg.daynightpvp.util.logging.Logger;

public class WorldGuardManager {

    public static void register() {
        Logger.verbose("Registering WorldGuard DaytimePvpFlag (Default: false)...");
        FlagManager.register("daytime-pvp", false);
    }

}
