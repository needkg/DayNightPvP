package me.needkg.daynightpvp.listeners;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.core.di.ConfigurationContainer;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.listeners.damage.EntityDamageListener;
import me.needkg.daynightpvp.listeners.damage.PotionSplashListener;
import me.needkg.daynightpvp.listeners.damage.ProjectileHitListener;
import me.needkg.daynightpvp.listeners.player.PlayerDeathListener;
import me.needkg.daynightpvp.listeners.player.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager {

    private final GeneralConfiguration generalConfiguration;

    public ListenerManager(ConfigurationContainer configurationContainer) {
        this.generalConfiguration = configurationContainer.getGeneralConfiguration();
    }

    public void register() {
        registerJoinListener();
        registerEntityDamageListener();
        registerPotionSplashListener();
        registerProjectileHitListener();
        registerDeathListener();
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
    }

    public void restart() {
        unregisterAll();
        register();
    }

    private void registerJoinListener() {
        if (generalConfiguration.getUpdateChecker()) {
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityDamageListener() {
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), DayNightPvP.getInstance());
    }

    private void registerPotionSplashListener() {
        Bukkit.getPluginManager().registerEvents(new PotionSplashListener(), DayNightPvP.getInstance());
    }

    private void registerProjectileHitListener() {
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), DayNightPvP.getInstance());
    }

    private void registerDeathListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), DayNightPvP.getInstance());
    }

}
