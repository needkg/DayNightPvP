package me.needkg.daynightpvp.listener.player;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import me.needkg.daynightpvp.service.player.SleepService;
import me.needkg.daynightpvp.util.player.PlayerNotifier;

public class PlayerBadLeaveListener implements Listener {

    public SleepService sleepService;

    public PlayerBadLeaveListener(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedLeaveEvent event) {

        Player player = event.getPlayer();
        
        sleepService.removeSleeping(player);

        String playerName = player.getName();
        World world = player.getWorld();
        int jogadoresDormindo = sleepService.getSleepingPlayersSize();
        int jogadoresOnline = Bukkit.getOnlinePlayers().size();
        double sleepPercentage = sleepService.getSleepingPercentage(world);

        Bukkit.broadcastMessage(playerName + " se levantou!");
        Bukkit.broadcastMessage("Jogadores dormindo: " + jogadoresDormindo + "/" + jogadoresOnline + " - " + sleepPercentage + "%");
        PlayerNotifier.broadcastActionBar(world, "Jogadores dormindo: " + jogadoresDormindo + "/" + jogadoresOnline + " - " + sleepPercentage + "%");
    }

}
