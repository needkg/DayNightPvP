package me.needkg.daynightpvp.listener.manager;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.integration.vault.LoseMoney;
import me.needkg.daynightpvp.listener.damage.EntityDamageListener;
import me.needkg.daynightpvp.listener.damage.PotionSplashListener;
import me.needkg.daynightpvp.listener.damage.ProjectileHitListener;
import me.needkg.daynightpvp.listener.player.PlayerBedEnterListener;
import me.needkg.daynightpvp.listener.player.PlayerBedLeaveListener;
import me.needkg.daynightpvp.listener.player.PlayerDeathListener;
import me.needkg.daynightpvp.listener.player.PlayerJoinListener;
import me.needkg.daynightpvp.service.player.SleepService;
import me.needkg.daynightpvp.service.update.UpdateService;
import me.needkg.daynightpvp.task.manager.TaskManager;
import me.needkg.daynightpvp.utis.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ListenerManager {

    private final GlobalConfigurationManager globalConfigurationManager;
    private final UpdateService updateService;
    private final WorldConfigurationManager worldConfigurationManager;
    private final LoseMoney loseMoney;
    private final GriefPreventionManager griefPreventionManager; 
    private final MessageManager messageManager;
    private final SleepService sleepService;
    private final TaskManager taskManager;

    public ListenerManager(MessageManager messageManager,GriefPreventionManager griefPreventionManager,GlobalConfigurationManager globalConfigurationManager, UpdateService updateService, WorldConfigurationManager worldConfigurationManager, LoseMoney loseMoney, SleepService sleepService, TaskManager taskManager) {
        this.messageManager = messageManager;
        this.griefPreventionManager = griefPreventionManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.updateService = updateService;
        this.worldConfigurationManager = worldConfigurationManager;
        this.loseMoney = loseMoney;
        this.sleepService = sleepService;
        this.taskManager = taskManager;
    }

    public void register() {
        registerPlayerJoinListener();
        registerEntityDamageListener();
        registerPotionSplashListener();
        registerProjectileHitListener();
        registerPlayerDeathListener();
        registerPlayerBedListener();
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

    private void registerPlayerBedListener() {
        Logger.verbose("Registering PlayerBed Enter/Leave listener...");
        Bukkit.getPluginManager().registerEvents(new PlayerBedEnterListener(sleepService, taskManager, worldConfigurationManager), DayNightPvP.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerBedLeaveListener(sleepService), DayNightPvP.getInstance());
    }

}
