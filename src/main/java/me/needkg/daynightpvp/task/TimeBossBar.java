package me.needkg.daynightpvp.task;

import me.needkg.daynightpvp.configuration.message.BossBarMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

public class TimeBossBar implements Runnable {

    private static final int DEFAULT_DURATION_SECONDS = 600;

    private final BossBarMessages bossBarMessages;
    private final BossBar bossBar;
    private final World world;
    private final int dayDurationSeconds;
    private final int nightDurationSeconds;
    private final int dayEnd;
    private final TimeDurationController timeDurationController;

    public TimeBossBar(BossBar bossbar, World world, boolean customDayNightDurationEnabled,
                       int dayDurationSeconds, int nightDurationSeconds, int dayEnd, TimeDurationController timeDurationController) {
        DependencyContainer container = DependencyContainer.getInstance();
        this.bossBarMessages = container.getMessageContainer().getBossBar();
        this.bossBar = bossbar;
        this.world = world;
        this.dayDurationSeconds = customDayNightDurationEnabled ? dayDurationSeconds : DEFAULT_DURATION_SECONDS;
        this.nightDurationSeconds = customDayNightDurationEnabled ? nightDurationSeconds : DEFAULT_DURATION_SECONDS;
        this.dayEnd = dayEnd;
        this.timeDurationController = timeDurationController;
    }

    @Override
    public void run() {
        updateBossBar();
        updateVisiblePlayers();
    }

    private void updateVisiblePlayers() {
        Bukkit.getServer().getOnlinePlayers().forEach(bossBar::removePlayer);
        world.getPlayers().forEach(bossBar::addPlayer);
    }

    private void updateBossBar() {
        double currentTime = getCurrentTime();
        boolean isDaytime = currentTime >= 0 && currentTime < dayEnd;

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
        double remainingTicks = dayEnd - currentTime;
        double progress = currentTime / dayEnd;
        long remainingSeconds = calculateRemainingSeconds(remainingTicks, dayEnd, dayDurationSeconds);

        bossBar.setProgress(progress);
        bossBar.setColor(BarColor.YELLOW);
        bossBar.setTitle(bossBarMessages.getUntilSunsetMessage().replace("{0}", formatTime(remainingSeconds)));
    }

    private void updateNighttimeBossBar(double currentTime) {
        double remainingTicks = 24000 - currentTime;
        double progress = (currentTime - dayEnd) / (24000 - dayEnd);
        long remainingSeconds = calculateRemainingSeconds(remainingTicks, 24000 - dayEnd, nightDurationSeconds);

        bossBar.setProgress(progress);
        bossBar.setColor(BarColor.PURPLE);
        bossBar.setTitle(bossBarMessages.getUntilSunriseMessage().replace("{0}", formatTime(remainingSeconds)));
    }

    private long calculateRemainingSeconds(double remainingTicks, double totalTicks, int durationSeconds) {
        return (long) ((remainingTicks / totalTicks) * durationSeconds);
    }

    private String formatTime(long remainingSeconds) {
        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
