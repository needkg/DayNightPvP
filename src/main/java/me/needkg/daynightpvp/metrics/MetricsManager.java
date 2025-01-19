package me.needkg.daynightpvp.metrics;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.utils.LoggingUtil;
import org.bstats.bukkit.Metrics;

public class MetricsManager {

    private static final int BSTATS_ID = 19067;

    public void start() {
        LoggingUtil.sendVerboseMessage("Starting bStats...");
        new Metrics(DayNightPvP.getInstance(), BSTATS_ID);
    }

}
