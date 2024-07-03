package org.callvdois.daynightpvp.metrics;

import org.callvdois.daynightpvp.DayNightPvP;

public class MetricsHandler {

    public void start() {
        int bStatsID = 19067;
        new Metrics(DayNightPvP.getInstance(), bStatsID);
    }

}
