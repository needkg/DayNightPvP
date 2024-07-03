package org.callvdois.daynightpvp.runnables;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.files.ConfigFile;

public class RunnableHandler {

    private final ConfigFile configFile;

    public RunnableHandler() {
        configFile = new ConfigFile();
    }

    public void startAllRunnables() {

        if (configFile.getDayNightDurationEnabled()) {
            DayNightPvP.serviceTasks.add(new CustomTimeDuration().runTaskTimer(DayNightPvP.getInstance(), 0, 1));
            for (World world : configFile.getDayNightDurationWorlds()) {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            }
        }

        DayNightPvP.serviceTasks.add(new AutomaticPvp().runTaskTimer(DayNightPvP.getInstance(), 0, 20));

    }

    private void stopRunnable(BukkitTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

    public void stopAllRunnables() {
        for (BukkitTask task : DayNightPvP.serviceTasks) {
            stopRunnable(task);
        }
        DayNightPvP.serviceTasks.clear();

        for (World world : configFile.getDayNightDurationWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }

    }

}
