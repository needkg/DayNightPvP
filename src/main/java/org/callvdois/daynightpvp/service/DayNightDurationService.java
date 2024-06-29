package org.callvdois.daynightpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callvdois.daynightpvp.config.ConfigManager;

public class DayNightDurationService extends BukkitRunnable {

    private final ConfigManager configManager;

    public DayNightDurationService() {
        configManager = new ConfigManager();
    }

    @Override
    public void run() {
        long dayTicks = 12000;
        long nightTicks = 12000;
        double dayTickIncrement = dayTicks / (configManager.getDayNightDurationDayDuration() * 20.0);
        double nightTickIncrement = nightTicks / (configManager.getDayNightDurationNightDuration() * 20.0);

        for (String worldName : configManager.getDayNightDurationWorlds()) {
            World world = Bukkit.getWorld(worldName);
            long time = world.getTime();
            if (time < 12000) {
                world.setTime((long) (time + dayTickIncrement));
            } else {
                world.setTime((long) (time + nightTickIncrement));
            }
        }

    }

}