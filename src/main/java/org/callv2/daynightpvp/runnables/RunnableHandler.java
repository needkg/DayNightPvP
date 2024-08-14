package org.callv2.daynightpvp.runnables;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitTask;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.WorldUtils;

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

        for (String worldName : configFile.getWorlds()) {
            if (WorldUtils.checkWorldIsValid(worldName)) {

                if (configFile.getDayNightDurationEnabled(worldName)) {
                    startCustomTimeDuration(
                            configFile.getAutomaticPvpDayEnd(worldName),
                            configFile.getDayNightDurationDayDuration(worldName),
                            configFile.getDayNightDurationNightDuration(worldName),
                            Bukkit.getWorld(worldName));
                }

                if (configFile.getAutomaticPvpEnabled(worldName)) {
                    startAutomaticPvp(
                            langFile,
                            configFile.getAutomaticPvpDayEnd(worldName),
                            configFile.getAutomaticDifficultyEnabled(worldName),
                            configFile.getNotifyPlayersTitleEnabled(worldName),
                            configFile.getNotifyPlayersSoundEnabled(worldName),
                            configFile.getAutomaticDifficultyDay(worldName),
                            configFile.getAutomaticDifficultyNight(worldName),
                            configFile.getNotifyPlayersSoundDay(worldName),
                            configFile.getNotifyPlayersSoundNight(worldName),
                            configFile.getNotifyPlayersTitleFadeIn(worldName),
                            configFile.getNotifyPlayersTitleStay(worldName),
                            configFile.getNotifyPlayersTitleFadeOut(worldName),
                            configFile.getNotifyPlayersSoundNightVolume(worldName),
                            configFile.getNotifyPlayersSoundDayVolume(worldName),
                            configFile.getNotifyPlayersChatDayNightStartsEnabled(worldName),
                            Bukkit.getWorld(worldName));
                }

            }
        }
    }

    private void startCustomTimeDuration(
            long dayTicks,
            int dayDuration,
            int nightDuration,
            World world) {
        BukkitTask customTimeTask = new CustomTimeDuration(dayTicks, dayDuration, nightDuration, world).runTaskTimer(DayNightPvP.getInstance(), 0, 1);
        serviceTasks.add(customTimeTask);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    private void startAutomaticPvp(
            LangFile langFile,
            long dayEnd,
            boolean automaticDifficultyEnabled,
            boolean notifyPlayersTitleEnabled,
            boolean notifyPlayersSoundEnabled,
            Difficulty automaticDifficultyDay,
            Difficulty automaticDifficultyNight,
            Sound notifyPlayersSoundDay,
            Sound notifyPlayersSoundNight,
            int fadeIn,
            int stay,
            int fadeOut,
            float soundNightVolume,
            float soundDayVolume,
            boolean notifyPlayersChatDayNightStarts,
            World world) {
        BukkitTask automaticPvpTask = new AutomaticPvp(
                langFile,
                dayEnd,
                automaticDifficultyEnabled,
                notifyPlayersTitleEnabled,
                notifyPlayersSoundEnabled,
                automaticDifficultyDay,
                automaticDifficultyNight,
                notifyPlayersSoundDay,
                notifyPlayersSoundNight,
                fadeIn,
                stay,
                fadeOut,
                soundNightVolume,
                soundDayVolume,
                notifyPlayersChatDayNightStarts,
                world).runTaskTimer(DayNightPvP.getInstance(), 0, 20);
        serviceTasks.add(automaticPvpTask);
    }

    public void stopAllRunnables() {
        for (BukkitTask task : serviceTasks) {
            stopRunnable(task);
        }
        serviceTasks.clear();

        for (String worldName : configFile.getWorlds()) {
            if (WorldUtils.checkWorldIsValid(worldName)) {
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            }
        }

    }

    private void stopRunnable(BukkitTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

}
