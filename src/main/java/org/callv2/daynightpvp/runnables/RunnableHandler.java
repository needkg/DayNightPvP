package org.callv2.daynightpvp.runnables;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;

import java.util.ArrayList;
import java.util.List;

public class RunnableHandler {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private List<BukkitTask> serviceTasks = new ArrayList<>();

    public RunnableHandler(ConfigFile configFile, LangFile langFile) {
        this.configFile = configFile;
        this.langFile = langFile;
    }

    public void startAllRunnables() {

        if (configFile.getDayNightDurationEnabled()) {
            startCustomTimeDuration();
        }

        startAutomaticPvp();
    }

    private void startCustomTimeDuration() {
        BukkitTask customTimeTask = new CustomTimeDuration(configFile).runTaskTimer(DayNightPvP.getInstance(), 0, 1);
        serviceTasks.add(customTimeTask);

        List<World> worlds = configFile.getDayNightDurationWorlds();
        for (World world : worlds) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    }

    private void startAutomaticPvp() {
        BukkitTask automaticPvpTask = new AutomaticPvp(configFile, langFile)
                .runTaskTimer(DayNightPvP.getInstance(), 0, 20);
        serviceTasks.add(automaticPvpTask);
    }

    public void stopAllRunnables() {
        for (BukkitTask task : serviceTasks) {
            stopRunnable(task);
        }
        serviceTasks.clear();

        List<World> worlds = configFile.getDayNightDurationWorlds();
        for (World world : worlds) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }
    }

    private void stopRunnable(BukkitTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

}
