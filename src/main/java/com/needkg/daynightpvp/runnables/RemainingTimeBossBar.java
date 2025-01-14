package com.needkg.daynightpvp.runnables;

import com.needkg.daynightpvp.config.settings.MessageSettings;
import com.needkg.daynightpvp.di.DependencyContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

public class RemainingTimeBossBar implements Runnable {

    private final MessageSettings messageSettings;
    private final BossBar bossBar;
    private final World world;
    private final boolean customDayNightDurationEnabled;
    private final int dayDurationTicks;
    private final int nightDurationTicks;
    private final int dayEnd;

    public RemainingTimeBossBar(BossBar bossbar, World world, boolean customDayNightDurationEnabled, int dayDurationSeconds, int nightDurationSeconds, int dayEnd) {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageSettings = container.getMessageSettings();
        this.bossBar = bossbar;
        this.world = world;
        this.customDayNightDurationEnabled = customDayNightDurationEnabled;
        this.dayDurationTicks = defineDurationTicks(dayDurationSeconds);
        this.nightDurationTicks = defineDurationTicks(nightDurationSeconds);
        this.dayEnd = dayEnd;
    }

    @Override
    public void run() {

        updateBossBar();

        Bukkit.getServer().getOnlinePlayers().forEach(bossBar::removePlayer);
        world.getPlayers().forEach(bossBar::addPlayer);
    }

    private void updateBossBar() {
        long time = world.getTime();

        long remainingTicks;
        double progress;
        String title;

        if (time >= 0 && time < dayEnd) {
            remainingTicks = dayEnd - time;
            progress = (double) time / dayEnd;
            title = messageSettings.getFeedbackBossbarSunset().replace("{0}", formatTime(remainingTicks, dayDurationTicks));
            bossBar.setColor(BarColor.YELLOW);
        } else {
            remainingTicks = 24000 - time;
            progress = (double) (time - dayEnd) / (24000 - dayEnd);
            title = messageSettings.getFeedbackBossbarSunrise().replace("{0}", formatTime(remainingTicks, nightDurationTicks));
            bossBar.setColor(BarColor.PURPLE);
        }

        bossBar.setProgress(progress);
        bossBar.setTitle(title);
    }

    private String formatTime(long remainingTicks, long totalTicks) {
        // Converte ticks para segundos
        long remainingSeconds = (long) (remainingTicks * (totalTicks / 12000.0) / 20);

        // Calcula horas, minutos e segundos
        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;

        // Formata a string com horas, minutos e segundos
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private int defineDurationTicks(int seconds) {
        if (customDayNightDurationEnabled) {
            return seconds * 20;
        } else {
            return 12000;
        }
    }
}
