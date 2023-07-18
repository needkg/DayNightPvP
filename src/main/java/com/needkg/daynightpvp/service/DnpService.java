package com.needkg.daynightpvp.service;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.PlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

import java.util.List;

public class DnpService {


    public void startService() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.plugin, () -> {
            listWorlds();
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
                    PlayerInteract.broadcastMessageToPlayers(world, LangManager.dayMessage);
                }
                if (ConfigManager.dayEnabled) {
                    PlayerInteract.playSoundToAllPlayers(world, ConfigManager.daySound, ConfigManager.dayVolume, ConfigManager.dayPitch);
                }
            }
        } else if (currentWorldTime >= ConfigManager.dayEnd) {
            world.setDifficulty(Difficulty.valueOf(ConfigManager.nightDifficulty.toUpperCase()));
            world.setPVP(true);

            if (ConfigManager.pvpAlert) {
                PlayerInteract.broadcastMessageToPlayers(world, LangManager.nightMessage);
            }
            if (ConfigManager.nightEnabled) {
                PlayerInteract.playSoundToAllPlayers(world, ConfigManager.nightSound, ConfigManager.nightVolume, ConfigManager.nightPitch);
            }
        }
    }

}