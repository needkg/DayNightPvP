package me.needkg.daynightpvp.task.display.bossbar;

import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.task.controller.time.TimeDurationController;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

public class TimeBossBar implements Runnable {

    private static final int DEFAULT_DURATION_SECONDS = 600;
    private static final double TICKS_PER_DAY = 24000.0;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    private final WorldConfigurationManager worldConfigurationManager;
    private final MessageManager messageManager;
    private final BossBar bossBar;
    private final World world;
    private final int dayDurationSeconds;
    private final int nightDurationSeconds;
    private final TimeDurationController timeDurationController;
    private final long pvpDayEnd;
    private final String dayMessage;
    private final String nightMessage;


    public TimeBossBar(BossBar bossbar, World world, String worldName, TimeDurationController timeDurationController) {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldConfigurationManager = container.getWorldConfigurationManager();
        this.messageManager = container.getMessageManager();

        this.bossBar = bossbar;
        this.world = world;
        this.pvpDayEnd = worldConfigurationManager.getPvpAutomaticDayEnd(worldName);
        this.dayDurationSeconds = worldConfigurationManager.isDayNightDurationEnabled(worldName)
                ? worldConfigurationManager.getDayNightDurationDayDuration(worldName)
                : DEFAULT_DURATION_SECONDS;
        this.nightDurationSeconds = worldConfigurationManager.isDayNightDurationEnabled(worldName)
                ? worldConfigurationManager.getDayNightDurationNightDuration(worldName)
                : DEFAULT_DURATION_SECONDS;
        this.timeDurationController = timeDurationController;
        this.dayMessage = messageManager.getMessage(Message.BOSSBAR_UNTIL_SUNSET);
        this.nightMessage = messageManager.getMessage(Message.BOSSBAR_UNTIL_SUNRISE);
    }

    @Override
    public void run() {
        updateBossBar();
        updateVisiblePlayers();
    }

    private void updateVisiblePlayers() {
        bossBar.getPlayers().forEach(bossBar::removePlayer);
        world.getPlayers().forEach(bossBar::addPlayer);
    }

    private void updateBossBar() {
        double currentTime = getCurrentTime();
        boolean isDaytime = currentTime < pvpDayEnd;

        if (isDaytime) {
            updateDaytimeBossBar(currentTime);
        } else {
            updateNighttimeBossBar(currentTime);
        }
    }

    private double getCurrentTime() {
        return timeDurationController != null ? timeDurationController.getVirtualTime() : world.getTime();
    }

    private void updateDaytimeBossBar(double currentTime) {
        double progress = currentTime / pvpDayEnd;
        long remainingSeconds = calculateRemainingSeconds(pvpDayEnd - currentTime, pvpDayEnd, dayDurationSeconds);

        bossBar.setProgress(progress);
        bossBar.setColor(BarColor.YELLOW);
        bossBar.setTitle(dayMessage.replace("{0}", formatTime(remainingSeconds)));
    }

    private void updateNighttimeBossBar(double currentTime) {
        double remainingTicks = TICKS_PER_DAY - currentTime;
        double nightProgress = (currentTime - pvpDayEnd) / (TICKS_PER_DAY - pvpDayEnd);
        long remainingSeconds = calculateRemainingSeconds(remainingTicks, TICKS_PER_DAY - pvpDayEnd, nightDurationSeconds);

        bossBar.setProgress(nightProgress);
        bossBar.setColor(BarColor.PURPLE);
        bossBar.setTitle(nightMessage.replace("{0}", formatTime(remainingSeconds)));
    }

    private long calculateRemainingSeconds(double remainingTicks, double totalTicks, int durationSeconds) {
        return (long) ((remainingTicks / totalTicks) * durationSeconds);
    }

    private String formatTime(long remainingSeconds) {
        long hours = remainingSeconds / SECONDS_PER_HOUR;
        long minutes = (remainingSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
        long seconds = remainingSeconds % SECONDS_PER_MINUTE;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
