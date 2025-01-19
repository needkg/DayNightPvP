package me.needkg.daynightpvp.listener.manager;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.listener.damage.EntityDamageListener;
import me.needkg.daynightpvp.listener.damage.PotionSplashListener;
import me.needkg.daynightpvp.listener.damage.ProjectileHitListener;
import me.needkg.daynightpvp.listener.player.PlayerDeathListener;
import me.needkg.daynightpvp.listener.player.PlayerJoinListener;
import me.needkg.daynightpvp.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager {

    private final GlobalConfigurationManager globalConfigurationManager;

    public ListenerManager(GlobalConfigurationManager globalConfigurationManager) {
        this.globalConfigurationManager = globalConfigurationManager;
    }

    public void register() {
        registerPlayerJoinListener();
        registerEntityDamageListener();
        registerPotionSplashListener();
        registerProjectileHitListener();
        registerPlayerDeathListener();
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
    }

    public void restart() {
        unregisterAll();
        register();
    }

    private void registerPlayerJoinListener() {
        if (globalConfigurationManager.isUpdateCheckerEnabled()) {
            Logger.verbose("Registering PlayerJoin listener...");
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityDamageListener() {
        Logger.verbose("Registering EntityDamage listener...");
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), DayNightPvP.getInstance());
    }

    private void registerPotionSplashListener() {
        Logger.verbose("Registering PotionSplash listener...");
        Bukkit.getPluginManager().registerEvents(new PotionSplashListener(), DayNightPvP.getInstance());
    }

    private void registerProjectileHitListener() {
        Logger.verbose("Registering ProjectileHit listener...");
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), DayNightPvP.getInstance());
    }

    private void registerPlayerDeathListener() {
        Logger.verbose("Registering PlayerDeath listener...");
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), DayNightPvP.getInstance());
    }

}
