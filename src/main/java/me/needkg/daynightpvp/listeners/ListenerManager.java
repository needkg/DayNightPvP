package me.needkg.daynightpvp.listeners;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.listeners.damage.EntityDamageListener;
import me.needkg.daynightpvp.listeners.damage.PotionSplashListener;
import me.needkg.daynightpvp.listeners.damage.ProjectileHitListener;
import me.needkg.daynightpvp.listeners.player.PlayerDeathListener;
import me.needkg.daynightpvp.listeners.player.PlayerJoinListener;
import me.needkg.daynightpvp.utils.LoggingUtil;
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
            LoggingUtil.sendVerboseMessage("Registering PlayerJoin listener...");
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityDamageListener() {
        LoggingUtil.sendVerboseMessage("Registering EntityDamage listener...");
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), DayNightPvP.getInstance());
    }

    private void registerPotionSplashListener() {
        LoggingUtil.sendVerboseMessage("Registering PotionSplash listener...");
        Bukkit.getPluginManager().registerEvents(new PotionSplashListener(), DayNightPvP.getInstance());
    }

    private void registerProjectileHitListener() {
        LoggingUtil.sendVerboseMessage("Registering ProjectileHit listener...");
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), DayNightPvP.getInstance());
    }

    private void registerPlayerDeathListener() {
        LoggingUtil.sendVerboseMessage("Registering PlayerDeath listener...");
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), DayNightPvP.getInstance());
    }

}
