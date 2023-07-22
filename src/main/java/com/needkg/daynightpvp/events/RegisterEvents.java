package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public class RegisterEvents {

    private final PluginManager pluginManager;

    public RegisterEvents() {
        pluginManager = Bukkit.getPluginManager();
    }

    public void register() {
        HandlerList.unregisterAll(DayNightPvP.plugin);
        registerJoinEvent();
        registerEntityEvent();
        registerPlayerEvent();
        registerInventoryEvent();
    }

    private void registerInventoryEvent() {
        pluginManager.registerEvents(new InventoryEvent(), DayNightPvP.plugin);
    }

    private void registerJoinEvent() {
        if (ConfigManager.updateChecker) {
            pluginManager.registerEvents(new JoinEvent(), DayNightPvP.plugin);
        }
    }

    private void registerEntityEvent() {
        if (PluginUtils.isPluginInstalled("GriefPrevention")) {
            if (!ConfigManager.griefPreventionPvpInLand) {
                pluginManager.registerEvents(new DamageEvent(), DayNightPvP.plugin);
            }
        }
    }

    private void registerPlayerEvent() {
        boolean vaultLoseMoneyOnDeath = ConfigManager.vaultLoseMoneyOnDeath;
        boolean keepInventoryWhenKilledByPlayer = ConfigManager.keepInventoryWhenKilledByPlayer;

        if(vaultLoseMoneyOnDeath || keepInventoryWhenKilledByPlayer) {
            pluginManager.registerEvents(new DeathEvent(), DayNightPvP.plugin);
        }
    }

}
