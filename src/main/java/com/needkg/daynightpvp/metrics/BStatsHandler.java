package com.needkg.daynightpvp.metrics;

import com.needkg.daynightpvp.DayNightPvP;
import org.bstats.bukkit.Metrics;

public class BStatsHandler {

    public void start() {
        int bStatsID = 19067;
        new Metrics(DayNightPvP.getInstance(), bStatsID);
    }

}
