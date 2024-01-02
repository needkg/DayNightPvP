package org.callvdois.daynightpvp.service;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeChecker {

    public static List<World> worldsPvpOff = new ArrayList<>();
    public static List<World> worldsPvpOn = new ArrayList<>();
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final PlayerUtils playerUtils;

    public TimeChecker() {
        configManager = new ConfigManager();
        langManager = new LangManager();
        playerUtils = new PlayerUtils();
    }

    public void run() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), this::listWorlds, 20L, 20L);
    }

    private void listWorlds() {
        List<String> worldsList = configManager.getList("daynightpvp.worlds");
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

    public boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        if (currentWorldTime < configManager.getInt("daynightpvp.day-end")) {
            if (!worldsPvpOff.contains(world)) {
                if (configManager.getBoolean("daynightpvp.automatic-difficulty.enabled")) {
                    world.setDifficulty(Difficulty.valueOf(configManager.getString("daynightpvp.automatic-difficulty.day").toUpperCase()));
                }
                if (configManager.getBoolean("notify-players.chat.day-night-starts")) {
                    PlayerUtils.sendMessageToAllPlayers(world, langManager.getString("notify-day-chat"));
                }
                if (configManager.getBoolean("notify-players.title.enabled")) {
                    playerUtils.sendTitleToAllPlayers(world, langManager.getString("notify-day-title"), langManager.getString("notify-day-subtitle"));
                }
                if (configManager.getBoolean("notify-players.sound.enabled")) {
                    PlayerUtils.playSoundToAllPlayers(world, Sound.valueOf(configManager.getString("notify-players.sound.day.sound")));
                }
            }
            return false;
        } else {
            if (!worldsPvpOn.contains(world)) {
                if (configManager.getBoolean("daynightpvp.automatic-difficulty.enabled")) {
                    world.setDifficulty(Difficulty.valueOf(configManager.getString("daynightpvp.automatic-difficulty.night").toUpperCase()));
                }
                if (configManager.getBoolean("notify-players.chat.day-night-starts")) {
                    PlayerUtils.sendMessageToAllPlayers(world, langManager.getString("notify-night-chat"));
                }
                if (configManager.getBoolean("notify-players.title.enabled")) {
                    playerUtils.sendTitleToAllPlayers(world, langManager.getString("notify-night-title"), langManager.getString("notify-night-subtitle"));
                }
                if (configManager.getBoolean("notify-players.sound.enabled")) {
                    PlayerUtils.playSoundToAllPlayers(world, Sound.valueOf(configManager.getString("notify-players.sound.night.sound")));
                }
            }
            return true;
        }
    }

}