package me.needkg.daynightpvp.task;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private final WorldConfiguration worldConfiguration;
    private final List<Integer> scheduledTasks;
    private final List<BossBar> activeBossBars;
    private final Map<String, TimeDurationController> worldTimeControllers;

    public TaskManager(WorldConfiguration worldConfiguration) {
        this.worldConfiguration = worldConfiguration;
        this.scheduledTasks = new ArrayList<>();
        this.activeBossBars = new ArrayList<>();
        this.worldTimeControllers = new HashMap<>();
    }

    public void startAllRunnables() {
        for (String worldName : worldConfiguration.getWorldNames()) {
            if (WorldUtil.isWorldValid(worldName)) {
                initializeWorldTasks(worldName);
            }
        }
    }

    private void initializeWorldTasks(String worldName) {
        World world = Bukkit.getWorld(worldName);
        TimeDurationController timeDurationController = null;

        if (worldConfiguration.getDayNightDurationEnabled(worldName)) {
            timeDurationController = initializeTimeController(worldName, world);
        }

        if (worldConfiguration.getTimeRemainingBossBarEnabled(worldName)) {
            initializeBossBar(worldName, world, timeDurationController);
        }

        if (worldConfiguration.getAutomaticPvpEnabled(worldName)) {
            initializeAutomaticPvP(worldName, world);
        }
    }

    private TimeDurationController initializeTimeController(String worldName, World world) {
        TimeDurationController timeDurationController = new TimeDurationController(
                worldConfiguration.getAutomaticPvpDayEnd(worldName),
                worldConfiguration.getDayNightDurationDayDuration(worldName),
                worldConfiguration.getDayNightDurationNightDuration(worldName),
                world);

        scheduledTasks.add(scheduleRepeatingTask(timeDurationController, 1));
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        worldTimeControllers.put(worldName, timeDurationController);

        return timeDurationController;
    }

    private void initializeBossBar(String worldName, World world, TimeDurationController timeDurationController) {
        BossBar bossBar = Bukkit.createBossBar("bossbar", BarColor.BLUE, BarStyle.SOLID);
        activeBossBars.add(bossBar);

        TimeBossBar bossBarTask = new TimeBossBar(
                bossBar,
                world,
                worldConfiguration.getDayNightDurationEnabled(worldName),
                worldConfiguration.getDayNightDurationDayDuration(worldName),
                worldConfiguration.getDayNightDurationNightDuration(worldName),
                worldConfiguration.getAutomaticPvpDayEnd(worldName),
                timeDurationController);

        scheduledTasks.add(scheduleRepeatingTask(bossBarTask, 20));
    }

    private void initializeAutomaticPvP(String worldName, World world) {
        WorldStateController pvpTask = new WorldStateController(
                worldConfiguration.getAutomaticPvpDayEnd(worldName),
                worldConfiguration.getAutomaticDifficultyEnabled(worldName),
                worldConfiguration.getNotifyPlayersTitleEnabled(worldName),
                worldConfiguration.getNotifyPlayersSoundEnabled(worldName),
                worldConfiguration.getAutomaticDifficultyDay(worldName),
                worldConfiguration.getAutomaticDifficultyNight(worldName),
                worldConfiguration.getNotifyPlayersSoundDay(worldName),
                worldConfiguration.getNotifyPlayersSoundNight(worldName),
                worldConfiguration.getNotifyPlayersTitleFadeIn(worldName),
                worldConfiguration.getNotifyPlayersTitleStay(worldName),
                worldConfiguration.getNotifyPlayersTitleFadeOut(worldName),
                worldConfiguration.getNotifyPlayersSoundNightVolume(worldName),
                worldConfiguration.getNotifyPlayersSoundDayVolume(worldName),
                worldConfiguration.getNotifyPlayersChatDayNightStartsEnabled(worldName),
                world);

        scheduledTasks.add(scheduleRepeatingTask(pvpTask, 20));
    }

    private int scheduleRepeatingTask(Runnable task, int tickInterval) {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), task, 0, tickInterval);
    }

    public void stopAllRunnables() {
        cancelAllTasks();
        clearWorldStates();
        removeBossBars();
        restoreWorldSettings();
    }

    private void cancelAllTasks() {
        scheduledTasks.forEach(Bukkit.getScheduler()::cancelTask);
        scheduledTasks.clear();
        worldTimeControllers.clear();
    }

    private void clearWorldStates() {
        WorldStateController.dayWorlds.clear();
        WorldStateController.nightWorlds.clear();
    }

    private void removeBossBars() {
        activeBossBars.forEach(BossBar::removeAll);
        activeBossBars.clear();
    }

    private void restoreWorldSettings() {
        worldConfiguration.getValidWorldNames().forEach(worldName ->
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
    }

    public void restart() {
        stopAllRunnables();
        startAllRunnables();
    }
}
