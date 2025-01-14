package me.needkg.daynightpvp.listeners;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.config.settings.GeneralSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

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
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), DayNightPvP.getInstance());
        }
    }

    private void registerEntityListener() {
        Bukkit.getPluginManager().registerEvents(new DamageListener(), DayNightPvP.getInstance());
    }

    private void registerDeathListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), DayNightPvP.getInstance());
    }

}
