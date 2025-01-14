package me.needkg.daynightpvp.metrics;

import me.needkg.daynightpvp.DayNightPvP;
import org.bstats.bukkit.Metrics;

public class BStatsHandler {

    private static final int BSTATS_ID = 19067;

    public void start() {
        new Metrics(DayNightPvP.getInstance(), BSTATS_ID);
    }

}
