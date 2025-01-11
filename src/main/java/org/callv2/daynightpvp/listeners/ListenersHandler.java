package org.callv2.daynightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;

public class ListenersHandler {

    private final ConfigFile configFile;

    public ListenersHandler(ConfigFile configFile) {
        this.configFile = configFile;
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
        Bukkit.getPluginManager().registerEvents(new DeathListener(), DayNightPvP.getInstance());
    }

}
