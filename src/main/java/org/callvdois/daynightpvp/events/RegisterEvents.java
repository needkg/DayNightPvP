package org.callvdois.daynightpvp.events;

import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;

public class RegisterEvents {

    private final PluginManager pluginManager;

    public RegisterEvents() {
        pluginManager = Bukkit.getPluginManager();
    }

    public void register() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
        registerJoinEvent();
        registerEntityEvent();
        registerPlayerEvent();
        registerInventoryEvent();
    }

    private void registerInventoryEvent() {
        pluginManager.registerEvents(new InventoryEvent(), DayNightPvP.getInstance());
    }

    private void registerJoinEvent() {
        if (ConfigManager.updateChecker) {
            pluginManager.registerEvents(new JoinEvent(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityEvent() {
        //if (PluginUtils.isPluginInstalled("GriefPrevention")) {
        //    if (!ConfigManager.griefPreventionPvpInLand) {
        //        pluginManager.registerEvents(new DamageEvent(), DayNightPvP.plugin);
        //    }
        //}
        pluginManager.registerEvents(new DamageEvent(), DayNightPvP.getInstance());
    }

    private void registerPlayerEvent() {
        boolean vaultLoseMoneyOnDeath = ConfigManager.vaultLoseMoneyOnDeath;
        boolean keepInventoryWhenKilledByPlayer = ConfigManager.keepInventoryWhenKilledByPlayer;

        if(vaultLoseMoneyOnDeath || keepInventoryWhenKilledByPlayer) {
            pluginManager.registerEvents(new DeathEvent(), DayNightPvP.getInstance());
        }
    }

}
