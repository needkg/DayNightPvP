package me.needkg.daynightpvp.runnables;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.List;

public class RunnableHandler {

    private final WorldSettings worldSettings;
    private List<Integer> tasks = new ArrayList<>();
    private List<BossBar> bossBarList = new ArrayList<>();

    public RunnableHandler(WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
    }

    public void startAllRunnables() {

        for (String worldName : worldSettings.getWorldNames()) {
            if (WorldUtils.isWorldValid(worldName)) {

                if (worldSettings.getDayNightDurationEnabled(worldName)) {
                    startCustomTimeDuration(
                            worldSettings.getAutomaticPvpDayEnd(worldName),
                            worldSettings.getDayNightDurationDayDuration(worldName),
                            worldSettings.getDayNightDurationNightDuration(worldName),
                            Bukkit.getWorld(worldName));
                }

                if (worldSettings.getTimeRemainingBossBarEnabled(worldName)) {
                    startRemainingTimeBossBar(
                            Bukkit.getWorld(worldName),
                            worldSettings.getDayNightDurationEnabled(worldName),
                            worldSettings.getDayNightDurationDayDuration(worldName),
                            worldSettings.getDayNightDurationNightDuration(worldName),
                            worldSettings.getAutomaticPvpDayEnd(worldName));
                }

                if (worldSettings.getAutomaticPvpEnabled(worldName)) {
                    startAutomaticPvp(
                            worldSettings.getAutomaticPvpDayEnd(worldName),
                            worldSettings.getAutomaticDifficultyEnabled(worldName),
                            worldSettings.getNotifyPlayersTitleEnabled(worldName),
                            worldSettings.getNotifyPlayersSoundEnabled(worldName),
                            worldSettings.getAutomaticDifficultyDay(worldName),
                            worldSettings.getAutomaticDifficultyNight(worldName),
                            worldSettings.getNotifyPlayersSoundDay(worldName),
                            worldSettings.getNotifyPlayersSoundNight(worldName),
                            worldSettings.getNotifyPlayersTitleFadeIn(worldName),
                            worldSettings.getNotifyPlayersTitleStay(worldName),
                            worldSettings.getNotifyPlayersTitleFadeOut(worldName),
                            worldSettings.getNotifyPlayersSoundNightVolume(worldName),
                            worldSettings.getNotifyPlayersSoundDayVolume(worldName),
                            worldSettings.getNotifyPlayersChatDayNightStartsEnabled(worldName),
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
        tasks.add(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), new RemainingTimeBossBar(bossbar, world, customDayNightDurationEnabled, dayDuration, nightDuration, dayEnd), 0, 20));
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

        for (String worldName : worldSettings.getValidWorldNames()) {
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }

    }

    public void restart() {
        stopAllRunnables();
        startAllRunnables();
    }

}
