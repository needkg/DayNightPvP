package org.callvdois.daynightpvp.worldguard;

import org.callvdois.daynightpvp.DayNightPvP;

public class RegisterCustomFlag {

    public void run() {
        if (DayNightPvP.worldGuardIsPresent) {
            AllowPvpOnDayFlag.register();
        }
    }

}
