package org.callv2.daynightpvp.bstats;

import org.bstats.bukkit.Metrics;
import org.callv2.daynightpvp.DayNightPvP;

public class BStatsHandler {

    public void start() {
        int bStatsID = 19067;
        new Metrics(DayNightPvP.getInstance(), bStatsID);
    }

}
