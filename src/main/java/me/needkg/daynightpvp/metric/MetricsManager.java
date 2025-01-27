package me.needkg.daynightpvp.metric;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.utis.logging.Logger;
import org.bstats.bukkit.Metrics;

public class MetricsManager {

    private static final int BSTATS_ID = 19067;

    public void start() {
        Logger.verbose("Starting bStats...");
        new Metrics(DayNightPvP.getInstance(), BSTATS_ID);
    }

}
