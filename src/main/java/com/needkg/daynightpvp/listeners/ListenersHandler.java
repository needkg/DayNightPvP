package com.needkg.daynightpvp.listeners;

import com.needkg.daynightpvp.config.settings.GeneralSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import com.needkg.daynightpvp.DayNightPvP;

public class ListenersHandler {

    private final GeneralSettings generalSettings;

    public ListenersHandler(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
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
        if (generalSettings.getUpdateChecker()) {
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
