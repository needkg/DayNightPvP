package org.callv2.daynightpvp.runnables;

import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.ConsoleUtils;
import org.callv2.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class AutomaticPvp extends BukkitRunnable {

    public static List<World> worldsPvpOff = new ArrayList<>();
    public static List<World> worldsPvpOn = new ArrayList<>();
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
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public AutomaticPvp(ConfigFile configFile, LangFile langFile) {
        this.dayEnd = configFile.getDayNightPvpDayEnd();
        this.automaticDifficultyEnabled = configFile.getDayNightPvpAutomaticDifficultyEnabled();
        this.notifyPlayersChat = configFile.getNotifyPlayersChatDayNightStarts();
        this.notifyPlayersTitleEnabled = configFile.getNotifyPlayersTitleEnabled();
        this.notifyPlayersSoundEnabled = configFile.getNotifyPlayersSoundEnabled();
        this.automaticDifficultyDay = configFile.getDayNightPvpAutomaticDifficultyDay();
        this.automaticDifficultyNight = configFile.getDayNightPvpAutomaticDifficultyNight();
        this.notifyDayChat = langFile.getNotifyDayChat();
        this.notifyDayTitle = langFile.getNotifyDayTitle();
        this.notifyDaySubtitle = langFile.getNotifyDaySubtitle();
        this.notifyNightChat = langFile.getNotifyNightChat();
        this.notifyNightTitle = langFile.getNotifyNightTitle();
        this.notifyNightSubtitle = langFile.getNotifyNightSubtitle();
        this.notifyPlayersSoundDay = configFile.getNotifyPlayersSoundDay();
        this.notifyPlayersSoundNight = configFile.getNotifyPlayersSoundNight();
        this.worldList = configFile.getDayNightPvpWorlds();
        this.fadeIn = configFile.getNotifyPlayersTitleFadeIn();
        this.stay = configFile.getNotifyPlayersTitleStay();
        this.fadeOut = configFile.getNotifyPlayersTitleFadeOut();
    }

    @Override
    public void run() {
        for (World world : worldList) {
            if (checkTime(world)) {
                handleNight(world);
            } else {
                handleDay(world);
            }
            verifyPvpStatus(world);
        }
    }

    private void handleNight(World world) {
        if (!worldsPvpOn.contains(world)) {
            worldsPvpOn.add(world);
            notifyPlayers(world, true);
            ConsoleUtils.sendInfoMessage("[DayNightPvP] It's night in \"" + world.getName() + "\"");
        }
        worldsPvpOff.remove(world);
    }

    private void handleDay(World world) {
        if (!worldsPvpOff.contains(world)) {
            worldsPvpOff.add(world);
            notifyPlayers(world, false);
            ConsoleUtils.sendInfoMessage("[DayNightPvP] It's day in \"" + world.getName() + "\"");
        }
        worldsPvpOn.remove(world);
    }

    public void verifyPvpStatus(World world) {
        if (!world.getPVP()) {
            ConsoleUtils.sendWarningMessage("[DayNightPvP] Warning! Another plugin forced PvP to be disabled in the world " + world.getName() + " , trying to resolve...");
        }
    }

    public boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        boolean isNight = currentWorldTime >= dayEnd;

        if (isNight) {
            if (!worldsPvpOn.contains(world)) {
                handleNight(world);
            }
        } else {
            if (!worldsPvpOff.contains(world)) {
                handleDay(world);
            }
        }

        return isNight;
    }

    private void notifyPlayers(World world, boolean isNight) {
        if (automaticDifficultyEnabled) {
            world.setDifficulty(isNight ? automaticDifficultyNight : automaticDifficultyDay);
        }
        if (notifyPlayersChat) {
            PlayerUtils.sendMessageToAllPlayers(world, isNight ? notifyNightChat : notifyDayChat);
        }
        if (notifyPlayersTitleEnabled) {
            PlayerUtils.sendTitleToAllPlayers(world,
                    isNight ? notifyNightTitle : notifyDayTitle,
                    isNight ? notifyNightSubtitle : notifyDaySubtitle,
                    fadeIn, stay, fadeOut);
        }
        if (notifyPlayersSoundEnabled) {
            PlayerUtils.playSoundToAllPlayers(world,
                    isNight ? notifyPlayersSoundNight : notifyPlayersSoundDay);
        }
    }

}