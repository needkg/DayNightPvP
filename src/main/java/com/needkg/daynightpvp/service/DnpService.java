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
        List<String> worldsList = ConfigManager.worldList;
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
            if (currentWorldTime < ConfigManager.autoPvpDayEnd) {
                world.setPVP(false);
                world.setDifficulty(Difficulty.valueOf(ConfigManager.autoPvpDayDifficulty.toUpperCase()));
                if (ConfigManager.alertPlayersChat) {
                    PlayerInteract.sendMessageToPlayers(world, LangManager.dayChatMessage);
                }
                if (ConfigManager.alertPlayersTitle) {
                    PlayerInteract.sendTitleToPlayers(world, LangManager.dayTitleMessage, LangManager.daySubTitleMessage);
                }
                if (ConfigManager.playSoundPvpOff) {
                    PlayerInteract.playSoundToAllPlayers(world, ConfigManager.playSoundPvpOffSound, ConfigManager.playSoundPvpOffVolume, ConfigManager.playSoundPvpOffPitch);
                }
            }
        } else if (currentWorldTime >= ConfigManager.autoPvpDayEnd) {
            world.setDifficulty(Difficulty.valueOf(ConfigManager.autoPvpNightDifficulty.toUpperCase()));
            world.setPVP(true);

            if (ConfigManager.alertPlayersChat) {
                PlayerInteract.sendMessageToPlayers(world, LangManager.nightChatMessage);
            }
            if (ConfigManager.alertPlayersTitle) {
                PlayerInteract.sendTitleToPlayers(world, LangManager.nightTitleMessage, LangManager.nightSubTitleMessage);
            }
            if (ConfigManager.playSoundPvpOn) {
                PlayerInteract.playSoundToAllPlayers(world, ConfigManager.playSoundPvpOnSound, ConfigManager.playSoundPvpOnVolume, ConfigManager.playSoundPvpOnPitch);
            }
        }
    }

}