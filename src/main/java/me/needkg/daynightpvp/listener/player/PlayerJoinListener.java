package me.needkg.daynightpvp.listener.player;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.service.update.UpdateService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final UpdateService updateService;

    public PlayerJoinListener() {
        updateService = new UpdateService();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("dnp.admin")) {
            Bukkit.getServer().getScheduler().runTaskLater(DayNightPvP.getInstance(), () -> updateService.checkUpdate(event), 100);
        }
    }

}
