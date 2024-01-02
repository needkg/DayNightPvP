package org.callvdois.daynightpvp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.service.UpdateChecker;

public class JoinEvent implements Listener {
    private final UpdateChecker updateChecker;

    public JoinEvent() {
        updateChecker = new UpdateChecker();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin")) {
            Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> updateChecker.checkUpdate(event));
        }
    }

}
