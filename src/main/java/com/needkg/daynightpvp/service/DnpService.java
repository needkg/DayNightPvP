package com.needkg.daynightpvp.service;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class DnpService {


    public void startService() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.plugin, () -> {
            if (ControlService.pvpControlStatus) {
                listWorlds();
            }
        }, 20L, 20L);
    }

    public void listWorlds() {
        List<String> worldsList = ConfigManager.worlds;
        World[] worlds = getWorlds(worldsList);

        for (World world : worlds) {
            checkTime(world);
        }
    }

    private World[] getWorlds(List<String> worldsList) {
        World[] worlds = new World[worldsList.size()];
        for (int i = 0; i < worldsList.size(); i++) {
            worlds[i] = Bukkit.getWorld(worldsList.get(i));
        }
        return worlds;
    }

    public void checkTime(World world) {
        if (world == null) {
            return;
        }

        long currentWorldTime = world.getTime();
        boolean currentWorldStatus = world.getPVP();

        if (currentWorldStatus) {
            if (currentWorldTime < ConfigManager.dayEnd) {
                world.setPVP(false);
                world.setDifficulty(Difficulty.valueOf(ConfigManager.dayDifficulty.toUpperCase()));
                if (ConfigManager.pvpAlert) {
                    broadcastMessageToPlayers(world, LangManager.dayMessage);
                }
                if (ConfigManager.dayEnabled) {
                    playSoundToPlayers(world, ConfigManager.daySound, ConfigManager.dayVolume, ConfigManager.dayPitch);
                }
            }
        } else if (currentWorldTime >= ConfigManager.dayEnd) {
            world.setDifficulty(Difficulty.valueOf(ConfigManager.nightDifficulty.toUpperCase()));
            world.setPVP(true);

            if (ConfigManager.pvpAlert) {
                broadcastMessageToPlayers(world, LangManager.nightMessage);
            }
            if (ConfigManager.nightEnabled) {
                playSoundToPlayers(world, ConfigManager.nightSound, ConfigManager.nightVolume, ConfigManager.nightPitch);
            }
        }
    }

    private void broadcastMessageToPlayers(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    private void playSoundToPlayers(World world, Sound sound, float volume, float pitch) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

}