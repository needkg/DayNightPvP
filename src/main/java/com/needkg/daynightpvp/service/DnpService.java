package com.needkg.daynightpvp.service;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

import java.util.List;

public class DnpService {


    public void startService() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.plugin, this::listWorlds, 20L, 20L);
    }

    private void listWorlds() {
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

    private void checkTime(World world) {
        if (world == null) {
            return;
        }

        long currentWorldTime = world.getTime();
        boolean currentWorldStatus = world.getPVP();

        if (currentWorldStatus) {
            if (currentWorldTime < ConfigManager.autoPvpDayEnd) {

                world.setPVP(false);

                if (ConfigManager.automaticDifficulty) {
                    world.setDifficulty(Difficulty.valueOf(ConfigManager.automaticDifficultyDay.toUpperCase()));
                }
                if (ConfigManager.alertPlayersChat) {
                    PlayerUtils.sendMessageToAllPlayers(world, LangManager.dayChatMessage);
                }
                if (ConfigManager.alertPlayersTitle) {
                    PlayerUtils.sendTitleToAllPlayers(world, LangManager.dayTitleMessage, LangManager.daySubTitleMessage);
                }
                if (ConfigManager.playSoundPvpOff) {
                    PlayerUtils.playSoundToAllPlayers(world, ConfigManager.playSoundPvpOffSound, ConfigManager.playSoundPvpOffVolume, ConfigManager.playSoundPvpOffPitch);
                }
            }
        } else if (currentWorldTime >= ConfigManager.autoPvpDayEnd) {
            world.setPVP(true);

            if (ConfigManager.automaticDifficulty) {
                world.setDifficulty(Difficulty.valueOf(ConfigManager.automaticDifficultyNight.toUpperCase()));
            }
            if (ConfigManager.alertPlayersChat) {
                PlayerUtils.sendMessageToAllPlayers(world, LangManager.nightChatMessage);
            }
            if (ConfigManager.alertPlayersTitle) {
                PlayerUtils.sendTitleToAllPlayers(world, LangManager.nightTitleMessage, LangManager.nightSubTitleMessage);
            }
            if (ConfigManager.playSoundPvpOn) {
                PlayerUtils.playSoundToAllPlayers(world, ConfigManager.playSoundPvpOnSound, ConfigManager.playSoundPvpOnVolume, ConfigManager.playSoundPvpOnPitch);
            }
        }
    }

}