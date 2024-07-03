package org.callvdois.daynightpvp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.services.UpdateServices;

public class JoinListener implements Listener {
    private final UpdateServices updateServices;

    public JoinListener() {
        updateServices = new UpdateServices();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin")) {
            Bukkit.getServer().getScheduler().runTaskLater(DayNightPvP.getInstance(), () -> updateServices.checkUpdate(event), 100);
        }
    }

}
