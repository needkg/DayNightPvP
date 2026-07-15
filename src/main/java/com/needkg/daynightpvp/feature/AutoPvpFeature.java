package com.needkg.daynightpvp.feature;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.needkg.daynightpvp.config.world.WorldConfig;
import com.needkg.daynightpvp.config.world.WorldConfigGateway;
import com.needkg.daynightpvp.event.handler.EntityDamageByEntityEventHandler;
import com.needkg.daynightpvp.event.handler.PotionSplashEventHandler;
import com.needkg.daynightpvp.event.handler.ProjectileHitEventHandler;
import com.needkg.daynightpvp.util.EntityUtils;

public class AutoPvpFeature implements
        EntityDamageByEntityEventHandler,
        PotionSplashEventHandler,
        ProjectileHitEventHandler {

    private static final String PERMISSION_IMMUNE = "dnp.immune";
    private static final String PERMISSION_BYPASS = "dnp.bypass";

    private final Map<String, Config> worldConfigs = new ConcurrentHashMap<>();
    private final AutoPvpFeature.Language languageConfigs;

    public AutoPvpFeature(
            Map<String, Config> worldConfigs,
            AutoPvpFeature.Language languageConfigs) {
        this.worldConfigs.putAll(worldConfigs);
        this.languageConfigs = languageConfigs;
    }

    @Override
    public void handle(ProjectileHitEvent event) {

        if (event.getEntity().getShooter() instanceof Player attacker) {
            final var worldConfig = worldConfigs.get(EntityUtils.getWorldName(event.getEntity()));
            event.setCancelled(shouldCancel(attacker, event.getEntity(), worldConfig));
        }

    }

    @Override
    public void handle(PotionSplashEvent event) {

        Boolean shouldCancel = false;
        if (event.getPotion().getShooter() instanceof Player attacker) {
            final var worldConfig = worldConfigs.get(EntityUtils.getWorldName(event.getEntity()));
            shouldCancel = shouldCancel(attacker, event.getEntity(), worldConfig);
        }

        if (shouldCancel)
            event
                    .getAffectedEntities()
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(entity -> (Player) entity)
                    .forEach(player -> event.setIntensity(player, 0.0));

    }

    @Override
    public void handle(EntityDamageByEntityEvent event) {

        final boolean shouldCancel = Optional.ofNullable(worldConfigs.get(EntityUtils.getWorldName(event.getEntity())))
                .map(config -> shouldCancel(event.getDamager(), event.getEntity(), config))
                .orElse(false);

        event.setCancelled(shouldCancel);

    }

    private static Boolean shouldCancel(
            Entity attacker,
            Entity victim,
            Config worldConfig) {

        Boolean shouldCancel = false;

        if (worldConfig == null || !worldConfig.enabled())
            return shouldCancel;

        if (isDay(victim, worldConfig.dayEnd())
                && isPvp(attacker, victim)) {
            shouldCancel = true;
        }

        if (attacker.hasPermission(PERMISSION_BYPASS)) {
            shouldCancel = false;
        }

        if (!shouldCancel
                && victim.hasPermission(PERMISSION_IMMUNE)
                && isPvp(attacker, victim)) {
            shouldCancel = true;
        }

        return shouldCancel;
    }

    private static Boolean isPvp(Entity entity1, Entity entity2) {
        return entity1 instanceof Player && entity2 instanceof Player;
    }

    private static Boolean isDay(Entity entity, Long dayEnd) {
        return entity.getWorld().getTime() < dayEnd;
    }

    public record Config(
            Boolean enabled,
            Long dayEnd) {
    }

    public record Language(
            String combatDisabled,
            String playerImmune,
            String selfImmune) {
    }

}
