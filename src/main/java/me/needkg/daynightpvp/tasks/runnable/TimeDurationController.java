package me.needkg.daynightpvp.tasks.runnable;

import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import org.bukkit.World;

public class TimeDurationController implements Runnable {

    private static final double TICKS_PER_DAY = 24000.0;
    private static final double TICKS_PER_SECOND = 20.0;

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final World world;
    private final long pvpDayEnd;
    private double tickAccumulator;
    private double virtualTime;
    private long lastRealTime;

    public TimeDurationController(World world, String worldName, WorldConfigurationManager worldConfigurationManager) {
        this.world = world;
        this.virtualTime = world.getTime();
        this.lastRealTime = world.getTime();
        this.pvpDayEnd = worldConfigurationManager.getPvpAutomaticDayEnd(worldName);

        this.dayTickIncrement = pvpDayEnd / (worldConfigurationManager.getDayNightDurationDayDuration(worldName) * TICKS_PER_SECOND);
        this.nightTickIncrement = (TICKS_PER_DAY - pvpDayEnd) / (worldConfigurationManager.getDayNightDurationNightDuration(worldName) * TICKS_PER_SECOND);
    }

    public double getVirtualTime() {
        return virtualTime;
    }

    public double getTickIncrement() {
        return tickAccumulator;
    }

    public void setTickIncrement(double tickIncrement) {
        this.tickAccumulator = tickIncrement;
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
            world.setTime((currentTime + ticksToAdvance) % (long) TICKS_PER_DAY);
            tickAccumulator -= ticksToAdvance;
        }
    }
}