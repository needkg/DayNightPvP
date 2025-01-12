package com.needkg.daynightpvp.bstats;

import org.bstats.bukkit.Metrics;
import com.needkg.daynightpvp.DayNightPvP;

public class BStatsHandler {

    public void start() {
        int bStatsID = 19067;
        new Metrics(DayNightPvP.getInstance(), bStatsID);
    }

}
