package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.configuration.config.DayNightDurationConfiguration;
import me.needkg.daynightpvp.configuration.config.PvpConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeDurationController implements Runnable {

    private static final double TICKS_PER_DAY = 24000.0;
    private static final double TICKS_PER_SECOND = 20.0;

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final World world;
    private final String worldName;
    private double tickAccumulator;
    private double virtualTime;
    private long lastRealTime;
    private final PvpConfiguration pvpConfiguration;
    private final DayNightDurationConfiguration dayNightDurationConfiguration;
    private final long pvpDayEnd;

    public TimeDurationController(World world, String worldName) {
        DependencyContainer container = DependencyContainer.getInstance();
        pvpConfiguration = container.getConfigurationContainer().getPvpConfiguration();
        dayNightDurationConfiguration = container.getConfigurationContainer().getDayNightDurationConfiguration();
        this.world = world;
        this.worldName = worldName;
        this.virtualTime = world.getTime();
        this.lastRealTime = world.getTime();
        this.pvpDayEnd = pvpConfiguration.getPvpAutomaticDayEnd(worldName);
        
        this.dayTickIncrement = pvpDayEnd / (dayNightDurationConfiguration.getDayNightDurationDayDuration(worldName) * TICKS_PER_SECOND);
        this.nightTickIncrement = (TICKS_PER_DAY - pvpDayEnd) / (dayNightDurationConfiguration.getDayNightDurationNightDuration(worldName) * TICKS_PER_SECOND);
    }

    public double getVirtualTime() {
        return virtualTime;
    }

    @Override
    public void run() {
        long currentTime = world.getTime();
        handleTimeChange(currentTime);
        updateTime(currentTime);
    }

    private void handleTimeChange(long currentTime) {
        long timeDifference = Math.abs(currentTime - lastRealTime);
        if (timeDifference > TICKS_PER_SECOND) {
            virtualTime = currentTime;
            tickAccumulator = 0;
        }
        lastRealTime = currentTime;
    }

    private void updateTime(long currentTime) {
        double increment = currentTime < pvpDayEnd ? dayTickIncrement : nightTickIncrement;
        updateVirtualTime(increment);
        updateWorldTime(currentTime, increment);
    }

    private void updateVirtualTime(double increment) {
        virtualTime = (virtualTime + increment) % TICKS_PER_DAY;
    }

    private void updateWorldTime(long currentTime, double increment) {
        tickAccumulator += increment;
        if (tickAccumulator >= 1.0) {
            long ticksToAdvance = (long) tickAccumulator;
            world.setTime((currentTime + ticksToAdvance) % (long)TICKS_PER_DAY);
            tickAccumulator -= ticksToAdvance;
        }
    }
}