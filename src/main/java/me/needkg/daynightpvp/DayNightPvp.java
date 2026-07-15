package me.needkg.daynightpvp;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.config.ResourceLoaderYaml;
import com.needkg.daynightpvp.event.listener.EntityDamageByEntityEventListener;
import com.needkg.daynightpvp.event.listener.PotionSplashEventListener;
import com.needkg.daynightpvp.event.listener.ProjectileHitEventListener;
import com.needkg.daynightpvp.factory.AutoPvpFactory;
import com.needkg.daynightpvp.factory.TitleNotificationFactory;
import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.TitleNotificationFeature;

public final class DayNightPvp extends JavaPlugin {

    @Override
    public void onEnable() {

        final var configuratedLang = "en";
        final var resourceResourceLanguage = new ResourceLoaderYaml(
                new File(getDataFolder(), "lang" + File.separator + configuratedLang + ".yml"));
        final var defaultResourceResourceLanguage = new ResourceLoaderYaml(
                new File(getDataFolder(), "lang" + File.separator + "en.yml"));
        final var resourceLoaderConfig = new ResourceLoaderYaml(new File(getDataFolder(), "worlds.yml"));

        TitleNotificationFeature titleNotificationFeature = TitleNotificationFactory.create(
                resourceLoaderConfig,
                resourceResourceLanguage,
                defaultResourceResourceLanguage);

        AutoPvpFeature autoPvpFeature = AutoPvpFactory.create(
                resourceLoaderConfig,
                resourceResourceLanguage,
                defaultResourceResourceLanguage);

        var entityDamageByEntityEventListener = new EntityDamageByEntityEventListener(Set.of(autoPvpFeature));
        var projectileHitEventListener = new ProjectileHitEventListener(Set.of(autoPvpFeature));
        var potionSplashEventListener = new PotionSplashEventListener(Set.of(autoPvpFeature));

        Bukkit.getPluginManager().registerEvents(entityDamageByEntityEventListener, this);
        Bukkit.getPluginManager().registerEvents(projectileHitEventListener, this);
        Bukkit.getPluginManager().registerEvents(potionSplashEventListener, this);

    }

    @Override
    public void onDisable() {
    }

}
