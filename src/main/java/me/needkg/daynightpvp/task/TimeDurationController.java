package me.needkg.daynightpvp.task;

import org.bukkit.World;

public class TimeDurationController implements Runnable {

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final long dayTicks;
    private final World world;
    private double tickAccumulator;
    private double virtualTime;
    private long lastRealTime;

    public TimeDurationController(long dayTicks, int dayDuration, int nightDuration, World world) {
        this.dayTicks = dayTicks;
        this.world = world;
        this.virtualTime = world.getTime();
        this.lastRealTime = world.getTime();
        this.dayTickIncrement = (double) dayTicks / (dayDuration * 20.0);
        this.nightTickIncrement = (double) (24000 - dayTicks) / (nightDuration * 20.0);
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
        if (timeDifference > 20) {
            virtualTime = currentTime;
            tickAccumulator = 0;
        }
        lastRealTime = currentTime;
    }

    private void updateTime(long currentTime) {
        double increment = currentTime < dayTicks ? dayTickIncrement : nightTickIncrement;
        updateVirtualTime(increment);
        updateWorldTime(currentTime, increment);
    }

    private void updateVirtualTime(double increment) {
        virtualTime = (virtualTime + increment) % 24000;
    }

    private void updateWorldTime(long currentTime, double increment) {
        tickAccumulator += increment;
        if (tickAccumulator >= 1.0) {
            long ticksToAdvance = (long) tickAccumulator;
            world.setTime((currentTime + ticksToAdvance) % 24000);
            tickAccumulator -= ticksToAdvance;
        }
    }
}