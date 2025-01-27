package me.needkg.daynightpvp.listener.player;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.service.player.SleepService;
import me.needkg.daynightpvp.task.manager.TaskManager;
import me.needkg.daynightpvp.utis.player.PlayerNotifier;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerBedEnterListener implements Listener {

    public SleepService sleepService;
    public TaskManager taskManager;
    public WorldConfigurationManager worldConfigurationManager;
    public final double PERCENTAGE_TO_ADVANCE = 50.0;

    public PlayerBedEnterListener(SleepService sleepService, TaskManager taskManager, WorldConfigurationManager worldConfigurationManager) {
        this.sleepService = sleepService;
        this.taskManager = taskManager;
        this.worldConfigurationManager = worldConfigurationManager;
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) {

        Player player = event.getPlayer();
        player.setSleepingIgnored(true);

        sleepService.addSleeping(player);

        String playerName = player.getName();
        World world = player.getWorld();
        String worldName = world.getName();


        int jogadoresDormindo = sleepService.getSleepingPlayersSize();
        int jogadoresOnline = Bukkit.getOnlinePlayers().size();
        double sleepPercentage = sleepService.getSleepingPercentage(world);

        Bukkit.broadcastMessage(playerName + " se deitou!");
        Bukkit.broadcastMessage("Jogadores dormindo: " + jogadoresDormindo + "/" + jogadoresOnline + " - " + sleepPercentage + "%");
        PlayerNotifier.broadcastActionBar(world, "Jogadores dormindo: " + jogadoresDormindo + "/" + jogadoresOnline + " - " + sleepPercentage + "%");

        if (sleepPercentage == PERCENTAGE_TO_ADVANCE) {

            Bukkit.broadcastMessage(PERCENTAGE_TO_ADVANCE + "% dos jogadores dormiram, avançando para o dia...");

            new BukkitRunnable() {
                @Override
                public void run() {

                    double currentTime = taskManager.getTickIncrement(worldName);
                    long targetTime = 1000;

                    // Avança o tempo em 100 ticks por iteração
                    currentTime += 20;

                    if (sleepService.getSleepingPercentage(world) < PERCENTAGE_TO_ADVANCE) {
                        currentTime = 0;
                        Bukkit.broadcastMessage("Parando porque a porcentagem de jogadores dormindo é menor que " + PERCENTAGE_TO_ADVANCE + "%");
                        this.cancel();
                    }

                    if (world.getTime() < targetTime) {
                        currentTime = 0;
                        Bukkit.broadcastMessage("Parando porque está de dia!");
                        world.setTime(0);
                        this.cancel();
                    }

                    taskManager.setTickIncrement(worldName, currentTime);
                }
            }.runTaskTimer(DayNightPvP.getInstance(), 0L, 1L);
        }
    }

}
