package com.needkg.daynightpvp.feature;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.needkg.daynightpvp.config.world.WorldConfigGateway;
import com.needkg.daynightpvp.config.world.NotificationsConfig;
import com.needkg.daynightpvp.config.world.WorldConfig;
import com.needkg.daynightpvp.event.DayStartEvent;
import com.needkg.daynightpvp.event.NightStartEvent;
import com.needkg.daynightpvp.event.handler.DayStartEventHandler;
import com.needkg.daynightpvp.event.handler.NightStartEventHandler;

public class ChatNotificationFeature implements
        DayStartEventHandler,
        NightStartEventHandler {

    private final WorldConfigGateway dnpWorldGateway;

    public ChatNotificationFeature(WorldConfigGateway dnpWorldGateway) {
        this.dnpWorldGateway = dnpWorldGateway;
    }

    @Override
    public void handle(DayStartEvent event) {
        getConfig(event.getWorldName())
                .filter(Config::enabled)
                .ifPresent(config -> sendMessageToPlayers(
                        Bukkit.getWorld(event.getWorldName()),
                        "Day has started!"));
    }

    @Override
    public void handle(NightStartEvent event) {
        getConfig(event.getWorldName())
                .filter(Config::enabled)
                .ifPresent(config -> sendMessageToPlayers(
                        Bukkit.getWorld(event.getWorldName()),
                        "Night has started!"));

    }

    private void sendMessageToPlayers(World world, String message) {
        world.getPlayers().forEach(player -> player.sendMessage(message));
    }

    public record Config(
            Boolean enabled,
            Boolean dayNightChange,
            Boolean noPvpWarn) {

    }

    private Optional<ChatNotificationFeature.Config> getConfig(String worldName) {
        return dnpWorldGateway
                .findByName(worldName)
                .flatMap(WorldConfig::notifications)
                .flatMap(NotificationsConfig::chat);
    }

}
