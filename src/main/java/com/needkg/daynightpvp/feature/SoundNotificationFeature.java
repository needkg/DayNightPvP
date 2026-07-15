package com.needkg.daynightpvp.feature;

import com.needkg.daynightpvp.config.world.WorldConfigGateway;
import com.needkg.daynightpvp.event.DayStartEvent;
import com.needkg.daynightpvp.event.NightStartEvent;
import com.needkg.daynightpvp.event.handler.DayStartEventHandler;
import com.needkg.daynightpvp.event.handler.NightStartEventHandler;

public class SoundNotificationFeature implements
        DayStartEventHandler,
        NightStartEventHandler {

    private final WorldConfigGateway dnpWorldGateway;

    public SoundNotificationFeature(WorldConfigGateway dnpWorldGateway) {
        this.dnpWorldGateway = dnpWorldGateway;
    }

    @Override
    public void handle(DayStartEvent event) {
        // event.getWorld().getPlayers().forEach(player -> player.sendMessage("Day has
        // started!"));
    }

    @Override
    public void handle(NightStartEvent event) {
        // event.getWorld().getPlayers().forEach(player -> player.sendMessage("Night has
        // started!"));
    }

    public record Config(
        boolean enabled) {
    }

}
