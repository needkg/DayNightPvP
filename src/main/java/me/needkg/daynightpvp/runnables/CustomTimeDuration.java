package me.needkg.daynightpvp.runnables;

import org.bukkit.World;

public class CustomTimeDuration implements Runnable {

    private final double dayTickIncrement;
    private final double nightTickIncrement;
    private final long dayTicks;
    private final World world;
    private double tickAccumulator;

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
        double increment;

        // Determina o incremento de tempo com base na hora do dia
        if (time < dayTicks) {
            increment = dayTickIncrement;
        } else {
            increment = nightTickIncrement;
        }

        // Adiciona o incremento ao acumulador
        tickAccumulator += increment;

        // Só avança o tempo quando o acumulador excede 1 tick
        if (tickAccumulator >= 1.0) {
            long ticksToAdvance = (long) tickAccumulator;
            world.setTime(time + ticksToAdvance);
            tickAccumulator -= ticksToAdvance; // Reduz o acumulador
        }
    }
}