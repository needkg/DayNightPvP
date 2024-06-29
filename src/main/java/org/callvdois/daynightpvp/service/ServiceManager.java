package org.callvdois.daynightpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;

public class ServiceManager {

    private final ConfigManager configManager;

    public ServiceManager() {
        configManager = new ConfigManager();
    }

    public void startServices() {
        DayNightPvP.serviceTasks.add(new TimeCheckerService().runTaskTimer(DayNightPvP.getInstance(), 0, 20));
        if (configManager.getDayNightDuration()) {
            DayNightPvP.serviceTasks.add(new DayNightDurationService().runTaskTimer(DayNightPvP.getInstance(), 0, 1));
            for (String worldName : configManager.getDayNightDurationWorlds()) {
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            }
        }
    }

    public void stopServices() {
        for (BukkitTask task : DayNightPvP.serviceTasks) {
            stopService(task);
        }
        DayNightPvP.serviceTasks.clear();
        for (String worldName : configManager.getDayNightDurationWorlds()) {
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }
    }

    private void stopService(BukkitTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

}
