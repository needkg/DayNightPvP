package com.needkg.daynightpvp.feature;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.needkg.daynightpvp.DnpWorldGateway;
import com.needkg.daynightpvp.config.WorldConfig;
import com.needkg.daynightpvp.event.handler.EntityDamageByEntityEventHandler;
import com.needkg.daynightpvp.event.handler.EntityExplodeEventHandler;
import com.needkg.daynightpvp.event.handler.PotionSplashEventHandler;
import com.needkg.daynightpvp.event.handler.ProjectileHitEventHandler;
import com.needkg.daynightpvp.event.listener.EntityUtils;

public class AutoPvpFeature implements
        EntityDamageByEntityEventHandler,
        PotionSplashEventHandler,
        ProjectileHitEventHandler,
        EntityExplodeEventHandler {

    private final DnpWorldGateway dnpWorldGateway;

    public AutoPvpFeature(DnpWorldGateway dnpWorldGateway) {
        this.dnpWorldGateway = dnpWorldGateway;
    }

    @Override
    public void handle(ProjectileHitEvent event) {

        getAutoPvpEnabled(EntityUtils.getWorldName(event.getEntity()))
                .filter(Config::enabled)
                .ifPresent(config -> {
                    if (event.getEntity().getShooter() instanceof Player player
                            && isPvp(player, event.getEntity())
                            && isDay(event.getEntity(), config.dayEnd())) {
                        event.setCancelled(true);
                    }
                });

    }

    @Override
    public void handle(PotionSplashEvent event) {

        getAutoPvpEnabled(EntityUtils.getWorldName(event.getEntity()))
                .filter(Config::enabled)
                .ifPresent(config -> {
                    if (event.getPotion().getShooter() instanceof Player)
                        event
                                .getAffectedEntities()
                                .stream()
                                .filter(entity -> entity instanceof Player)
                                .map(entity -> (Player) entity)
                                .filter(entity -> isDay(entity, config.dayEnd()))
                                .forEach(player -> event.setIntensity(player, 0.0));
                });

    }

    @Override
    public void handle(EntityDamageByEntityEvent event) {

        getAutoPvpEnabled(EntityUtils.getWorldName(event.getEntity()))
                .filter(Config::enabled)
                .ifPresent(config -> {
                    if (isPvp(event.getDamager(), event.getEntity())
                            && event.getEntity().getWorld().getTime() < config.dayEnd()) {
                        event.setCancelled(true);
                        System.out.println("''DayNightFeature''' EntityDamageByEntityEvent handled (Evento Cancelado)");
                        return;
                    }
                });

    }

    @Override
    public void handle(EntityExplodeEvent event) {

        // final var dayNightConfig =
        // getAutoPvpEnabled(EntityUtils.getWorldName(event.getEntity()));

        // if (dayNightConfig.isEmpty())
        // return;

        // final var config = dayNightConfig.get();

        // if (!config.enabled()) {
        // return;
        // }

        // if (event.getPrimerEntity() instanceof Player player &&
        // event.getPrimingEntity() instanceof TNTPrimed tnt) {

        // owners.put(tnt.getUniqueId(), player.getUniqueId());
        // }
        // }

        // UUID owner = tnt.getSource().getUniqueId();

        // if (owner == null) {
        // return;
        // }

        // event.setCancelled(true);
        // System.out.println("''DayNightFeature''' EntityExplodeEvent handled (Evento
        // Cancelado), TNT Owner: " + owner.toString());
        // return;

    }

    private static Boolean isPvp(Entity entity1, Entity entity2) {
        return entity1 instanceof Player && entity2 instanceof Player;
    }

    private static Boolean isDay(Entity entity, Long dayEnd) {
        return entity.getWorld().getTime() < dayEnd;
    }

    private Optional<AutoPvpFeature.Config> getAutoPvpEnabled(String worldName) {
        return dnpWorldGateway.findByName(worldName).flatMap(WorldConfig::pvp);
    }

    public record Config(
            Boolean enabled,
            Long dayEnd) {

    }

}
