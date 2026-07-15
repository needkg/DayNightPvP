package com.needkg.daynightpvp.feature;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.needkg.daynightpvp.event.DayStartEvent;
import com.needkg.daynightpvp.event.NightStartEvent;
import com.needkg.daynightpvp.event.handler.DayStartEventHandler;
import com.needkg.daynightpvp.event.handler.NightStartEventHandler;

public class TitleNotificationFeature implements
        DayStartEventHandler,
        NightStartEventHandler {

    private final Map<String, Config> worldConfigs = new ConcurrentHashMap<>();
    private final Language languageConfigs;

    public TitleNotificationFeature(
            Map<String, Config> worldConfigs,
            Language languageConfigs) {
        this.worldConfigs.putAll(worldConfigs);
        this.languageConfigs = languageConfigs;
    }

    @Override
    public void handle(DayStartEvent event) {
        Optional.ofNullable(worldConfigs.get(event.getWorldName()))
                .filter(Config::enabled)
                .ifPresent(config -> {
                    sendTitleToPlayers(
                            Bukkit.getWorld(event.getWorldName()),
                            languageConfigs.dayTitle(),
                            languageConfigs.daySubtitle(),
                            config);
                });
    }

    @Override
    public void handle(NightStartEvent event) {
        Optional.ofNullable(worldConfigs.get(event.getWorldName()))
                .filter(Config::enabled)
                .ifPresent(config -> {
                    sendTitleToPlayers(
                            Bukkit.getWorld(event.getWorldName()),
                            languageConfigs.nightTitle(),
                            languageConfigs.nightSubtitle(),
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

    public record Language(
            String dayTitle,
            String daySubtitle,
            String nightTitle,
            String nightSubtitle) {

    }

}
