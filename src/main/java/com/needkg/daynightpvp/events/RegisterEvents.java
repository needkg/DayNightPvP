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
        HandlerList.unregisterAll(joinEvent);
        HandlerList.unregisterAll(entityEvent);

        boolean updateChecker = ConfigManager.updateChecker;
        boolean griefpreventionPvpProtection = ConfigManager.griefPreventionPvpProtection;

        if (updateChecker) {
            pluginManager.registerEvents(new JoinEvent(), DayNightPvP.plugin);
        }

        if (griefpreventionPvpProtection) {
            if (Bukkit.getPluginManager().getPlugin("GriefPrevention") != null) {
                consoleUtils.sendMessage("GriefPrevention detected, starting compatibility...");
                pluginManager.registerEvents(new EntityEvent(), DayNightPvP.plugin);
            }
        }
    }

}
