package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public class RegisterEvents {

    private final PluginManager pluginManager;

    public RegisterEvents() {
        this.pluginManager = Bukkit.getPluginManager();
    }

    public void register() {
        HandlerList.unregisterAll(DayNightPvP.plugin);
        registerJoinEvent();
        registerEntityEvent();
        registerInventoryEvent();
        registerPlayerEvent();
    }

    public void registerInventoryEvent() {
        pluginManager.registerEvents(new InventoryEvent(), DayNightPvP.plugin);
    }

    public void registerJoinEvent() {
        boolean updateChecker = ConfigManager.updateChecker;
        if (updateChecker) {
            pluginManager.registerEvents(new JoinEvent(), DayNightPvP.plugin);
        }
    }

    public void registerEntityEvent() {
        boolean griefpreventionPvpProtection = ConfigManager.griefPreventionPvpProtection;
        if (griefpreventionPvpProtection && Bukkit.getPluginManager().getPlugin("GriefPrevention") != null) {
                pluginManager.registerEvents(new EntityEvent(), DayNightPvP.plugin);
        }
    }

    public void registerPlayerEvent() {
        boolean vaultEnabled = ConfigManager.vaultEnabled;
        boolean loseMoneyEnabled = ConfigManager.loseMoneyEnabled;
        if (vaultEnabled && loseMoneyEnabled && Bukkit.getPluginManager().getPlugin("Vault") != null) {
            pluginManager.registerEvents(new PlayerEvent(), DayNightPvP.plugin);
        }
    }

}
