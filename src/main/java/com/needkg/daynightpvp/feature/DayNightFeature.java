package com.needkg.daynightpvp.feature;

import java.util.Optional;

import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.needkg.daynightpvp.DnpWorld;
import com.needkg.daynightpvp.DnpWorldGateway;
import com.needkg.daynightpvp.event.handler.EntityDamageByEntityEventHandler;
import com.needkg.daynightpvp.event.handler.PotionSplashEventHandler;
import com.needkg.daynightpvp.event.handler.ProjectileHitEventHandler;
import com.needkg.daynightpvp.event.listener.EntityUtils;

public class DayNightFeature implements
        EntityDamageByEntityEventHandler,
        PotionSplashEventHandler,
        ProjectileHitEventHandler {

    private final DnpWorldGateway dnpWorldGateway;

    public DayNightFeature(DnpWorldGateway dnpWorldGateway) {
        this.dnpWorldGateway = dnpWorldGateway;
    }

    @Override
    public void handle(ProjectileHitEvent event) {

        final var dayNightConfigOpt = getDayNightFeatureConfig(EntityUtils.getWorldName(event.getEntity()));

        if (dayNightConfigOpt.isEmpty())
            return;

        final var config = dayNightConfigOpt.get();

        if (config.enabled()) {

            System.out.println("''DayNightFeature''' ProjectileHitEvent handled (Evento Cancelado)");

            event.setCancelled(true);

        }

        System.out.println("''DayNightFeature''' ProjectileHitEvent handled (Evento Não Cancelado)");

    }

    @Override
    public void handle(PotionSplashEvent event) {

        final var dayNightConfig = getDayNightFeatureConfig(EntityUtils.getWorldName(event.getEntity()));

        if (dayNightConfig.isEmpty())
            return;

        // TODO Auto-generated method stub
        System.out.println("''DayNightFeature''' PotionSplashEvent handled");
    }

    @Override
    public void handle(EntityDamageByEntityEvent event) {

        final var dayNightConfig = getDayNightFeatureConfig(EntityUtils.getWorldName(event.getEntity()));

        if (dayNightConfig.isEmpty())
            return;

        // TODO Auto-generated method stub
        System.out.println("''DayNightFeature''' EntityDamageByEntityEvent handled");
    }

    private Optional<DayNightFeatureConfig> getDayNightFeatureConfig(String worldName) {
        return dnpWorldGateway.findByName(worldName).flatMap(DnpWorld::dayNight);
    }

}
