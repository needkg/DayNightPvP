package com.needkg.daynightpvp;

import java.io.File;
import java.util.Set;

import com.needkg.daynightpvp.event.listener.EntityDamageByEntityEventListener;
import com.needkg.daynightpvp.event.listener.PlayerDeathEventListener;
import com.needkg.daynightpvp.event.listener.PotionSplashEventListener;
import com.needkg.daynightpvp.event.listener.ProjectileHitEventListener;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.TestFeature;

public class Main {

    public static void main(String[] args) {

        // final var worldGateway = new DnpWorldGatewayYaml(new File(getDataFolder(), "worlds.yml").toURI());

        AutoPvpFeature dayNightFeature = null;
        TestFeature testFeature = new TestFeature();

        var playerDeathEventListener = new PlayerDeathEventListener(Set.of());

        var entityDamageByEntityEventListener = new EntityDamageByEntityEventListener(Set.of(dayNightFeature));
        var projectileHitEventListener = new ProjectileHitEventListener(Set.of(dayNightFeature));
        var potionSplashEventListener = new PotionSplashEventListener(Set.of(dayNightFeature));

        System.out.println("This is a Minecraft plugin and cannot be run as a standalone application.");

        // new DnpWorldGatewayYaml(
        //         new java.io.File("/home/jhonatapers/needkg/DayNightPvP/src/main/resources/worlds.yml").toURI())
        //         .findByName("world");

    }

}