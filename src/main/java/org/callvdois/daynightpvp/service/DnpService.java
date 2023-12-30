package org.callvdois.daynightpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class DnpService {

    public static List<World> worldsPvpOff = new ArrayList<>();
    public static List<World> worldsPvpOn = new ArrayList<>();

    public void startService() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), this::listWorlds, 20L, 20L);
    }

    private void listWorlds() {
        List<String> worldsList = ConfigManager.worldList;
        World[] worlds = getWorlds(worldsList);

        for (World world : worlds) {
            if (world != null) {
                if (checkTime(world)) {
                    handleNight(world);
                } else {
                    handleDay(world);
                }
            }
        }
    }

    private void handleNight(World world) {
        if (!worldsPvpOn.contains(world)) {
            worldsPvpOn.add(world);
            ConsoleUtils.info("DayNightPvP - It's night in \"" + world.getName() + "\"");
        }
        worldsPvpOff.remove(world);
    }

    private void handleDay(World world) {
        if (!worldsPvpOff.contains(world)) {
            worldsPvpOff.add(world);
            ConsoleUtils.info("DayNightPvP - It's day in \"" + world.getName() + "\"");
        }
        worldsPvpOn.remove(world);
    }

    private World[] getWorlds(List<String> worldsList) {
        World[] worlds = new World[worldsList.size()];
        for (int i = 0; i < worldsList.size(); i++) {
            worlds[i] = Bukkit.getWorld(worldsList.get(i));
        }
        return worlds;
    }

    private boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        if (currentWorldTime < ConfigManager.autoPvpDayEnd) {
            if (!worldsPvpOff.contains(world)) {
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
            return false;
        } else {
            if (!worldsPvpOn.contains(world)) {
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
            return true;
        }
    }

}