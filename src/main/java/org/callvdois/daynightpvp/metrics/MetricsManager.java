package org.callvdois.daynightpvp.metrics;

import org.callvdois.daynightpvp.DayNightPvP;

public class MetricsManager {

    public void register() {
        new Metrics(DayNightPvP.getInstance(), 19067);
    }

}
