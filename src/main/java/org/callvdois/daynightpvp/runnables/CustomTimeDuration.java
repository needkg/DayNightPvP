package org.callvdois.daynightpvp.runnables;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callvdois.daynightpvp.files.ConfigFile;

import java.util.List;

public class CustomTimeDuration extends BukkitRunnable {

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final List<World> worldList;

    public CustomTimeDuration() {
        ConfigFile configFile = new ConfigFile();
        long dayTicks = 12000;
        long nightTicks = 12000;
        dayTickIncrement = dayTicks / (configFile.getDayNightDurationDayDuration() * 20.0);
        nightTickIncrement = nightTicks / (configFile.getDayNightDurationNightDuration() * 20.0);
        worldList = configFile.getDayNightDurationWorlds();
    }

    @Override
    public void run() {
        for (World world : worldList) {
            long time = world.getTime();
            if (time < 12000) {
                world.setTime((long) (time + dayTickIncrement));
            } else {
                world.setTime((long) (time + nightTickIncrement));
            }
        }

    }

}