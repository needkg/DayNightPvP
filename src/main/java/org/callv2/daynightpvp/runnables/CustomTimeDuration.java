package org.callv2.daynightpvp.runnables;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callv2.daynightpvp.files.ConfigFile;

import java.util.List;

public class CustomTimeDuration extends BukkitRunnable {

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final List<World> worldList;
    private final long dayTicks;

    public CustomTimeDuration(ConfigFile configFile) {

        dayTicks = configFile.getDayNightPvpDayEnd();
        long nightTicks = 24000 - dayTicks;

        dayTickIncrement = dayTicks / (configFile.getDayNightDurationDayDuration() * 20.0);
        nightTickIncrement = nightTicks / (configFile.getDayNightDurationNightDuration() * 20.0);

        worldList = configFile.getDayNightDurationWorlds();
    }

    @Override
    public void run() {
        for (World world : worldList) {
            long time = world.getTime();
            if (time < dayTicks) {
                world.setTime((long) (time + dayTickIncrement));
            } else {
                world.setTime((long) (time + nightTickIncrement));
            }
        }

    }

}