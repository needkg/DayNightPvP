package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.config.BossbarConfiguration;
import me.needkg.daynightpvp.configuration.config.DayNightDurationConfiguration;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.config.PvpConfiguration;
import me.needkg.daynightpvp.core.di.ConfigurationContainer;
import me.needkg.daynightpvp.utils.WorldUtil;
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

    private final GeneralConfiguration generalConfiguration;
    private final DayNightDurationConfiguration dayNightDurationConfiguration;
    private final BossbarConfiguration bossbarConfiguration;
    private final PvpConfiguration pvpConfiguration;
    private final List<Integer> scheduledTasks;
    private final List<BossBar> activeBossBars;
    private final Map<String, TimeDurationController> worldTimeControllers;

    public TaskManager(ConfigurationContainer configurationContainer) {
        this.generalConfiguration = configurationContainer.getGeneralConfiguration();
        this.dayNightDurationConfiguration = configurationContainer.getDayNightDurationConfiguration();
        this.bossbarConfiguration = configurationContainer.getBossbarConfiguration();
        this.pvpConfiguration = configurationContainer.getPvpConfiguration();
        this.scheduledTasks = new ArrayList<>();
        this.activeBossBars = new ArrayList<>();
        this.worldTimeControllers = new HashMap<>();
    }

    public void startAllTasks() {
        for (String worldName : generalConfiguration.getWorldNames()) {
            if (WorldUtil.isWorldValid(worldName)) {
                initializeWorldTasks(worldName);
            }
        }
    }

    private void initializeWorldTasks(String worldName) {
        World world = Bukkit.getWorld(worldName);
        TimeDurationController timeDurationController = null;

        if (dayNightDurationConfiguration.getDayNightDurationEnabled(worldName)) {
            timeDurationController = initializeTimeController(worldName, world);
        }

        if (bossbarConfiguration.getBossbarEnabled(worldName)) {
            initializeBossBar(worldName, world, timeDurationController);
        }

        if (pvpConfiguration.getPvpAutomaticEnabled(worldName)) {
            initializeAutomaticPvP(worldName, world);
        }
    }

    private TimeDurationController initializeTimeController(String worldName, World world) {
        TimeDurationController timeDurationController = new TimeDurationController(world, worldName);

        scheduledTasks.add(scheduleRepeatingTask(timeDurationController, 1));
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        worldTimeControllers.put(worldName, timeDurationController);

        return timeDurationController;
    }

    private void initializeBossBar(String worldName, World world, TimeDurationController timeDurationController) {
        BossBar bossBar = Bukkit.createBossBar("bossbar", BarColor.BLUE, BarStyle.SOLID);
        activeBossBars.add(bossBar);

        TimeBossBar bossBarTask = new TimeBossBar(bossBar, world, worldName, timeDurationController);

        scheduledTasks.add(scheduleRepeatingTask(bossBarTask, 20));
    }

    private void initializeAutomaticPvP(String worldName, World world) {
        WorldStateController pvpTask = new WorldStateController(world, worldName);

        scheduledTasks.add(scheduleRepeatingTask(pvpTask, 20));
    }

    private int scheduleRepeatingTask(Runnable task, int tickInterval) {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), task, 0, tickInterval);
    }

    public void stopAllTasks() {
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
        generalConfiguration.getValidWorldNames().forEach(worldName ->
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
    }

    public void restart() {
        stopAllTasks();
        startAllTasks();
    }
}
