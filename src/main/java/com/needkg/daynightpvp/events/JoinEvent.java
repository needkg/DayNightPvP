package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.service.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    private final UpdateChecker updateChecker;

    public JoinEvent() {
        updateChecker = new UpdateChecker();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin") && ConfigManager.updateChecker) {
            Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.plugin, () -> {
                updateChecker.checkUpdate(event);
            });
        }
    }

}
