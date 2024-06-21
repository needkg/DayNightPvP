package org.callvdois.daynightpvp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;

public class RegisterEvents {

    private final PluginManager pluginManager;
    private final ConfigManager configManager;

    public RegisterEvents() {
        pluginManager = Bukkit.getPluginManager();
        configManager = new ConfigManager();
    }

    public void register() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
        registerJoinEvent();
        registerEntityEvent();
        registerDeathEvent();
        registerInventoryEvent();
    }

    private void registerInventoryEvent() {
        pluginManager.registerEvents(new InventoryEvent(), DayNightPvP.getInstance());
    }

    private void registerJoinEvent() {
        if (configManager.getUpdateChecker()) {
            pluginManager.registerEvents(new JoinEvent(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityEvent() {
        pluginManager.registerEvents(new DamageEvent(), DayNightPvP.getInstance());
    }

    private void registerDeathEvent() {
        boolean vaultLoseMoneyOnDeath = configManager.getVaultLoseMoneyOnDeathEnabled();
        boolean keepInventoryWhenKilledByPlayer = configManager.getPvpKeepInventoryWhenKilledByPlayer();

        if (vaultLoseMoneyOnDeath || keepInventoryWhenKilledByPlayer) {
            pluginManager.registerEvents(new DeathEvent(), DayNightPvP.getInstance());
        }
    }

}
