package com.needkg.daynightpvp;

import java.util.Set;

import com.needkg.daynightpvp.event.listener.PlayerDeathEventListener;
import com.needkg.daynightpvp.feature.KeepXpInPvpFeature;
import com.needkg.daynightpvp.feature.factory.KeepXpInPvpFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.event.listener.EntityDamageByEntityEventListener;
import com.needkg.daynightpvp.event.listener.PotionSplashEventListener;
import com.needkg.daynightpvp.event.listener.ProjectileHitEventListener;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.TitleNotificationFeature;
import com.needkg.daynightpvp.feature.factory.AutoPvpFactory;
import com.needkg.daynightpvp.feature.factory.TitleNotificationFactory;

public class FeatureManager {

    private final JavaPlugin plugin;

    public FeatureManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(
            ResourceLoader resourceLoaderWorld,
            ResourceLoader resourceLoaderLanguage) {

        HandlerList.unregisterAll(plugin);

        TitleNotificationFeature titleNotificationFeature = TitleNotificationFactory.create(
                resourceLoaderWorld,
                resourceLoaderLanguage);

        AutoPvpFeature autoPvpFeature = AutoPvpFactory.create(
                resourceLoaderWorld,
                resourceLoaderLanguage);

        KeepXpInPvpFeature keepXpInPvpFeature = KeepXpInPvpFactory.create(
                resourceLoaderWorld,
                resourceLoaderLanguage);

        var entityDamageByEntityEventListener = new EntityDamageByEntityEventListener(Set.of(autoPvpFeature));
        var projectileHitEventListener = new ProjectileHitEventListener(Set.of(autoPvpFeature));
        var potionSplashEventListener = new PotionSplashEventListener(Set.of(autoPvpFeature));
        var playerDeathEventListener = new PlayerDeathEventListener(Set.of(keepXpInPvpFeature));

        Bukkit.getPluginManager().registerEvents(entityDamageByEntityEventListener, plugin);
        Bukkit.getPluginManager().registerEvents(projectileHitEventListener, plugin);
        Bukkit.getPluginManager().registerEvents(potionSplashEventListener, plugin);
        Bukkit.getPluginManager().registerEvents(playerDeathEventListener, plugin);

    }

    public void shutdown() {
        HandlerList.unregisterAll(plugin);
    }

}