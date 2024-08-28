package org.callv2.daynightpvp.runnables;

import org.bukkit.World;

public class CustomTimeDuration implements Runnable {

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final long dayTicks;
    private final World world;

    public CustomTimeDuration(
            long dayTicks,
            int dayDuration,
            int nightDuration,
            World world) {

        this.dayTicks = dayTicks;
        long nightTicks = 24000 - dayTicks;
        this.world = world;

        dayTickIncrement = dayTicks / (dayDuration * 20.0);
        nightTickIncrement = nightTicks / (nightDuration * 20.0);

    }

    @Override
    public void run() {
        long time = world.getTime();
        if (time < dayTicks) {
            world.setTime((long) (time + dayTickIncrement));
        } else {
            world.setTime((long) (time + nightTickIncrement));
        }
    }

}