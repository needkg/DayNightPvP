package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public class RegisterEvents {

    private final PluginManager pluginManager;
    private final ConsoleUtils consoleUtils;
    private final JoinEvent joinEvent;
    private final EntityEvent entityEvent;

    public RegisterEvents() {
        this.pluginManager = Bukkit.getPluginManager();
        this.consoleUtils = new ConsoleUtils();
        this.joinEvent = new JoinEvent();
        this.entityEvent = new EntityEvent();
    }

    public void register() {
        HandlerList.unregisterAll(DayNightPvP.plugin);
        registerJoinEvent();
        registerEntityEvent();
        registerInventoryEvent();
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

        if (griefpreventionPvpProtection) {
            if (Bukkit.getPluginManager().getPlugin("GriefPrevention") != null) {
                consoleUtils.sendMessage("[DayNightPvP] GriefPrevention detected, starting compatibility...");
                pluginManager.registerEvents(new EntityEvent(), DayNightPvP.plugin);
            }
        }
    }

}
