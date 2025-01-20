package me.needkg.daynightpvp.listener.manager;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.vault.LoseMoney;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.listener.damage.EntityDamageListener;
import me.needkg.daynightpvp.listener.damage.PotionSplashListener;
import me.needkg.daynightpvp.listener.damage.ProjectileHitListener;
import me.needkg.daynightpvp.listener.player.PlayerDeathListener;
import me.needkg.daynightpvp.listener.player.PlayerJoinListener;
import me.needkg.daynightpvp.service.update.UpdateService;
import me.needkg.daynightpvp.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager {

    private final GlobalConfigurationManager globalConfigurationManager;
    private final UpdateService updateService;
    private final WorldConfigurationManager worldConfigurationManager;
    private final LoseMoney loseMoney;
    private final GriefPreventionManager griefPreventionManager; 
    private final MessageManager messageManager;

    public ListenerManager(MessageManager messageManager,GriefPreventionManager griefPreventionManager,GlobalConfigurationManager globalConfigurationManager, UpdateService updateService, WorldConfigurationManager worldConfigurationManager, LoseMoney loseMoney) {
        this.messageManager = messageManager;
        this.griefPreventionManager = griefPreventionManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.updateService = updateService;
        this.worldConfigurationManager = worldConfigurationManager;
        this.loseMoney = loseMoney;
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
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(updateService), DayNightPvP.getInstance());
        }
    }

    private void registerEntityDamageListener() {
        Logger.verbose("Registering EntityDamage listener...");
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(griefPreventionManager, messageManager, worldConfigurationManager), DayNightPvP.getInstance());
    }

    private void registerPotionSplashListener() {
        Logger.verbose("Registering PotionSplash listener...");
        Bukkit.getPluginManager().registerEvents(new PotionSplashListener(griefPreventionManager, messageManager, worldConfigurationManager), DayNightPvP.getInstance());
    }

    private void registerProjectileHitListener() {
        Logger.verbose("Registering ProjectileHit listener...");
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(griefPreventionManager, messageManager, worldConfigurationManager), DayNightPvP.getInstance());
    }

    private void registerPlayerDeathListener() {
        Logger.verbose("Registering PlayerDeath listener...");
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(worldConfigurationManager, loseMoney), DayNightPvP.getInstance());
    }

}
