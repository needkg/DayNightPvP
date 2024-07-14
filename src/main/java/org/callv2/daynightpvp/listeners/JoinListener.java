package org.callv2.daynightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.services.UpdateServices;

public class JoinListener implements Listener {

    private final UpdateServices updateServices;

    public JoinListener(ConfigFile configFile) {
        updateServices = new UpdateServices(configFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin")) {
            Bukkit.getServer().getScheduler().runTaskLater(DayNightPvP.getInstance(), () -> updateServices.checkUpdate(event), 100);
        }
    }

}
