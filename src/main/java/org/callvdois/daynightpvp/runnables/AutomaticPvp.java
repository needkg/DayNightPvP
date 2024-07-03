package org.callvdois.daynightpvp.runnables;

import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class AutomaticPvp extends BukkitRunnable {

    public static List<World> worldsPvpOff = new ArrayList<>();
    public static List<World> worldsPvpOn = new ArrayList<>();
    private final PlayerUtils playerUtils;
    private final long dayEnd;
    private final boolean automaticDifficultyEnabled;
    private final boolean notifyPlayersChat;
    private final boolean notifyPlayersTitleEnabled;
    private final boolean notifyPlayersSoundEnabled;
    private final Difficulty automaticDifficultyDay;
    private final Difficulty automaticDifficultyNight;
    private final String notifyDayChat;
    private final String notifyDayTitle;
    private final String notifyDaySubtitle;
    private final String notifyNightChat;
    private final String notifyNightTitle;
    private final String notifyNightSubtitle;
    private final Sound notifyPlayersSoundDay;
    private final Sound notifyPlayersSoundNight;
    private final List<World> worldList;

    public AutomaticPvp() {
        ConfigFile configFile = new ConfigFile();
        LangFile langFile = new LangFile();
        playerUtils = new PlayerUtils();
        dayEnd = configFile.getDayNightPvpDayEnd();
        automaticDifficultyEnabled = configFile.getDayNightPvpAutomaticDifficultyEnabled();
        notifyPlayersChat = configFile.getNotifyPlayersChatDayNightStarts();
        notifyPlayersTitleEnabled = configFile.getNotifyPlayersTitleEnabled();
        notifyPlayersSoundEnabled = configFile.getNotifyPlayersSoundEnabled();
        automaticDifficultyDay = configFile.getDayNightPvpAutomaticDifficultyDay();
        automaticDifficultyNight = configFile.getDayNightPvpAutomaticDifficultyNight();
        notifyDayChat = langFile.getNotifyDayChat();
        notifyDayTitle = langFile.getNotifyDayTitle();
        notifyDaySubtitle = langFile.getNotifyDaySubtitle();
        notifyNightChat = langFile.getNotifyNightChat();
        notifyNightTitle = langFile.getNotifyNightTitle();
        notifyNightSubtitle = langFile.getNotifyNightSubtitle();
        notifyPlayersSoundDay = configFile.getNotifyPlayersSoundDay();
        notifyPlayersSoundNight = configFile.getNotifyPlayersSoundNight();
        worldList = configFile.getDayNightPvpWorlds();
    }

    @Override
    public void run() {
        for (World world : worldList) {
            if (checkTime(world)) {
                handleNight(world);
            } else {
                handleDay(world);
            }
        }
    }

    private void handleNight(World world) {
        if (!worldsPvpOn.contains(world)) {
            worldsPvpOn.add(world);
            //ConsoleUtils.info("[DayNightPvP] It's night in \"" + world.getName() + "\"");
        }
        worldsPvpOff.remove(world);
    }

    private void handleDay(World world) {
        if (!worldsPvpOff.contains(world)) {
            worldsPvpOff.add(world);
            //ConsoleUtils.info("[DayNightPvP] It's day in \"" + world.getName() + "\"");
        }
        worldsPvpOn.remove(world);
    }

    public boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        boolean isNight = currentWorldTime >= dayEnd;

        if (isNight) {
            if (!worldsPvpOn.contains(world)) {
                handleNight(world);
                notifyNightActions(world);
            }
        } else {
            if (!worldsPvpOff.contains(world)) {
                handleDay(world);
                notifyDayActions(world);
            }
        }

        return isNight;
    }

    private void notifyNightActions(World world) {
        if (automaticDifficultyEnabled) {
            world.setDifficulty(automaticDifficultyNight);
        }
        if (notifyPlayersChat) {
            playerUtils.sendMessageToAllPlayers(world, notifyNightChat);
        }
        if (notifyPlayersTitleEnabled) {
            playerUtils.sendTitleToAllPlayers(world, notifyNightTitle, notifyNightSubtitle);
        }
        if (notifyPlayersSoundEnabled) {
            playerUtils.playSoundToAllPlayers(world, notifyPlayersSoundNight);
        }
    }

    private void notifyDayActions(World world) {
        if (automaticDifficultyEnabled) {
            world.setDifficulty(automaticDifficultyDay);
        }
        if (notifyPlayersChat) {
            playerUtils.sendMessageToAllPlayers(world, notifyDayChat);
        }
        if (notifyPlayersTitleEnabled) {
            playerUtils.sendTitleToAllPlayers(world, notifyDayTitle, notifyDaySubtitle);
        }
        if (notifyPlayersSoundEnabled) {
            playerUtils.playSoundToAllPlayers(world, notifyPlayersSoundDay);
        }
    }

}