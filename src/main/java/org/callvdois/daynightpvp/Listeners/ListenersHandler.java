package org.callvdois.daynightpvp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.files.ConfigFile;

public class ListenersHandler {

    private final ConfigFile configFile;

    public ListenersHandler() {
        configFile = new ConfigFile();
    }

    public void register() {
        registerJoinListener();
        registerEntityListener();
        registerDeathListener();
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
    }

    private void registerJoinListener() {
        if (configFile.getUpdateChecker()) {
            Bukkit.getPluginManager().registerEvents(new JoinListener(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityListener() {
        Bukkit.getPluginManager().registerEvents(new DamageListener(), DayNightPvP.getInstance());
    }

    private void registerDeathListener() {
        boolean vaultLoseMoneyOnDeath = configFile.getVaultLoseMoneyOnDeathEnabled();
        boolean keepInventoryWhenKilledByPlayer = configFile.getPvpKeepInventoryWhenKilledByPlayer();

        if (vaultLoseMoneyOnDeath || keepInventoryWhenKilledByPlayer) {
            Bukkit.getPluginManager().registerEvents(new DeathListener(), DayNightPvP.getInstance());
        }
    }

}
