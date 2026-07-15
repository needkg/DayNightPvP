package com.needkg.daynightpvp.feature;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.needkg.daynightpvp.config.world.WorldConfigGateway;
import com.needkg.daynightpvp.config.world.NotificationsConfig;
import com.needkg.daynightpvp.config.world.WorldConfig;
import com.needkg.daynightpvp.event.DayStartEvent;
import com.needkg.daynightpvp.event.NightStartEvent;
import com.needkg.daynightpvp.event.handler.DayStartEventHandler;
import com.needkg.daynightpvp.event.handler.NightStartEventHandler;

public class TitleNotificationFeature implements
        DayStartEventHandler,
        NightStartEventHandler {

    private final WorldConfigGateway dnpWorldGateway;

    private final String titleDay = "OLA OLAD DEI";
    private final String subTitleDay = "OLA OLAD DEI";
    private final String titleNight = "OLA OLA NaiTi";
    private final String subTitleNight = "OLA OLA NaiTi";

    public TitleNotificationFeature(
            WorldConfigGateway dnpWorldGateway,
            String titleDay,
            String titleNight) {
        this.dnpWorldGateway = dnpWorldGateway;
    }

    @Override
    public void handle(DayStartEvent event) {
        getConfig(event.getWorldName())
                .filter(Config::enabled)
                .ifPresent(config -> {
                    sendTitleToPlayers(
                            Bukkit.getWorld(event.getWorldName()),
                            titleDay,
                            "",
                            config);
                });
    }

    @Override
    public void handle(NightStartEvent event) {
        getConfig(event.getWorldName())
                .filter(Config::enabled)
                .ifPresent(config -> {
                    sendTitleToPlayers(
                            Bukkit.getWorld(event.getWorldName()),
                            titleNight,
                            "",
                            config);
                });
    }

    private void sendTitleToPlayers(World world, String title, String subtitle, Config config) {
        List<Player> players = world.getPlayers();
        players.forEach(player -> player.sendTitle(title, subtitle, config.fadeIn(), config.stay(), config.fadeOut()));
    }

    public record Config(
            Boolean enabled,
            int fadeIn,
            int stay,
            int fadeOut) {
    }

    private Optional<TitleNotificationFeature.Config> getConfig(String worldName) {
        return dnpWorldGateway
                .findByName(worldName)
                .flatMap(WorldConfig::notifications)
                .flatMap(NotificationsConfig::title);
    }
}
