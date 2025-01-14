package com.needkg.daynightpvp.listeners;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.services.UpdateServices;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final UpdateServices updateServices;

    public PlayerJoinListener() {
        updateServices = new UpdateServices();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin")) {
            Bukkit.getServer().getScheduler().runTaskLater(DayNightPvP.getInstance(), () -> updateServices.checkUpdate(event), 100);
        }
    }

}
