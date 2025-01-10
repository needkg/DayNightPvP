package org.callv2.daynightpvp.runnables;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class RunnableHandler {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private List<Integer> tasks = new ArrayList<>();
    private List<BossBar> bossBarList = new ArrayList<>();

    public RunnableHandler(ConfigFile configFile, LangFile langFile) {
        this.configFile = configFile;
        this.langFile = langFile;
    }

    public void startAllRunnables() {

        for (String worldName : configFile.getWorldNames()) {
            if (WorldUtils.isWorldValid(worldName)) {

                if (configFile.getDayNightDurationEnabled(worldName)) {
                    startCustomTimeDuration(
                            configFile.getAutomaticPvpDayEnd(worldName),
                            configFile.getDayNightDurationDayDuration(worldName),
                            configFile.getDayNightDurationNightDuration(worldName),
                            Bukkit.getWorld(worldName));
                }

                if (configFile.getTimeRemainingBossBarEnabled(worldName)) {
                    startRemainingTimeBossBar(
                            Bukkit.getWorld(worldName),
                            configFile.getDayNightDurationEnabled(worldName),
                            configFile.getDayNightDurationDayDuration(worldName),
                            configFile.getDayNightDurationNightDuration(worldName),
                            configFile.getAutomaticPvpDayEnd(worldName));
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
        tasks.add(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), new CustomTimeDuration(dayTicks, dayDuration, nightDuration, world), 0, 1));
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
        tasks.add(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), new AutomaticPvp(
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
                world), 0, 20));
    }

    private void startRemainingTimeBossBar(
            World world,
            boolean customDayNightDurationEnabled,
            int dayDuration,
            int nightDuration,
            int dayEnd) {
        BossBar bossbar = Bukkit.createBossBar("bossbar", BarColor.BLUE, BarStyle.SOLID);
        bossBarList.add(bossbar);
        tasks.add(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), new RemainingTimeBossBar(langFile, bossbar, world, customDayNightDurationEnabled, dayDuration, nightDuration, dayEnd), 0, 20));
    }

    public void stopAllRunnables() {

        for (int task : tasks) {
            Bukkit.getScheduler().cancelTask(task);
        }

        tasks.clear();

        AutomaticPvp.dayWorlds.clear();
        AutomaticPvp.nightWorlds.clear();

        for (BossBar bossBar : bossBarList) {
            bossBar.removeAll();
        }

        for (String worldName : configFile.getValidWorldNames()) {
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }

    }

}
