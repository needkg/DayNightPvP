package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.configuration.config.DayNightDurationConfiguration;
import me.needkg.daynightpvp.configuration.config.PvpConfiguration;
import me.needkg.daynightpvp.configuration.message.BossBarMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

public class TimeBossBar implements Runnable {

    private static final int DEFAULT_DURATION_SECONDS = 600;
    private static final double TICKS_PER_DAY = 24000.0;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    private final BossBarMessages bossBarMessages;
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
        DayNightDurationConfiguration dayNightDurationConfiguration = container.getConfigurationContainer().getDayNightDurationConfiguration();
        this.bossBarMessages = container.getMessageContainer().getBossBar();
        PvpConfiguration pvpConfiguration = container.getConfigurationContainer().getPvpConfiguration();
        
        this.bossBar = bossbar;
        this.world = world;
        this.pvpDayEnd = pvpConfiguration.getPvpAutomaticDayEnd(worldName);
        this.dayDurationSeconds = dayNightDurationConfiguration.getDayNightDurationEnabled(worldName) 
            ? dayNightDurationConfiguration.getDayNightDurationDayDuration(worldName) 
            : DEFAULT_DURATION_SECONDS;
        this.nightDurationSeconds = dayNightDurationConfiguration.getDayNightDurationEnabled(worldName) 
            ? dayNightDurationConfiguration.getDayNightDurationNightDuration(worldName) 
            : DEFAULT_DURATION_SECONDS;
        this.timeDurationController = timeDurationController;
        this.dayMessage = bossBarMessages.getUntilSunsetMessage();
        this.nightMessage = bossBarMessages.getUntilSunriseMessage();
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
