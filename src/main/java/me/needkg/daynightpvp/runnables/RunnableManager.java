package me.needkg.daynightpvp.runnables;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.utils.WorldUtils;
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

public class RunnableManager {

    private final WorldSettings worldSettings;
    private final List<Integer> scheduledTasks;
    private final List<BossBar> activeBossBars;
    private final Map<String, TimeController> worldTimeControllers;

    public RunnableManager(WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
        this.scheduledTasks = new ArrayList<>();
        this.activeBossBars = new ArrayList<>();
        this.worldTimeControllers = new HashMap<>();
    }

    public void startAllRunnables() {
        for (String worldName : worldSettings.getWorldNames()) {
            if (WorldUtils.isWorldValid(worldName)) {
                initializeWorldTasks(worldName);
            }
        }
    }

    private void initializeWorldTasks(String worldName) {
        World world = Bukkit.getWorld(worldName);
        TimeController timeController = null;

        if (worldSettings.getDayNightDurationEnabled(worldName)) {
            timeController = initializeTimeController(worldName, world);
        }

        if (worldSettings.getTimeRemainingBossBarEnabled(worldName)) {
            initializeBossBar(worldName, world, timeController);
        }

        if (worldSettings.getAutomaticPvpEnabled(worldName)) {
            initializeAutomaticPvP(worldName, world);
        }
    }

    private TimeController initializeTimeController(String worldName, World world) {
        TimeController timeController = new TimeController(
                worldSettings.getAutomaticPvpDayEnd(worldName),
                worldSettings.getDayNightDurationDayDuration(worldName),
                worldSettings.getDayNightDurationNightDuration(worldName),
                world);

        scheduledTasks.add(scheduleRepeatingTask(timeController, 1));
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        worldTimeControllers.put(worldName, timeController);

        return timeController;
    }

    private void initializeBossBar(String worldName, World world, TimeController timeController) {
        BossBar bossBar = Bukkit.createBossBar("bossbar", BarColor.BLUE, BarStyle.SOLID);
        activeBossBars.add(bossBar);

        TimeBossBar bossBarTask = new TimeBossBar(
                bossBar,
                world,
                worldSettings.getDayNightDurationEnabled(worldName),
                worldSettings.getDayNightDurationDayDuration(worldName),
                worldSettings.getDayNightDurationNightDuration(worldName),
                worldSettings.getAutomaticPvpDayEnd(worldName),
                timeController);

        scheduledTasks.add(scheduleRepeatingTask(bossBarTask, 20));
    }

    private void initializeAutomaticPvP(String worldName, World world) {
        WorldStateController pvpTask = new WorldStateController(
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
        worldSettings.getValidWorldNames().forEach(worldName ->
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
    }

    public void restart() {
        stopAllRunnables();
        startAllRunnables();
    }
}
