package org.callv2.daynightpvp.runnables;

import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.LoggingUtils;
import org.callv2.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class AutomaticPvp implements Runnable {

    public static List<World> dayWorlds = new ArrayList<>();
    public static List<World> nightWorlds = new ArrayList<>();
    private final long dayEnd;
    private final boolean automaticDifficultyEnabled;
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
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;
    private final float soundNightVolume;
    private final float soundDayVolume;
    private final boolean notifyPlayersChatDayNightStarts;
    private final World world;

    public AutomaticPvp(
            LangFile langFile,
            long dayEnd,
            boolean automaticDifficultyEnabled,
            boolean notifyPlayersTitleEnabled,
            boolean notifyPlayersSoundEnabled,
            Difficulty automaticDifficultyDay,
            Difficulty automaticDifficultyNight,
            Sound notifyPlayersSoundDay,
            Sound notifyPlayersSoundNight,
            int fadeIn,
            int stay,
            int fadeOut,
            float soundNightVolume,
            float soundDayVolume,
            boolean notifyPlayersChatDayNightStarts,
            World world) {
        this.dayEnd = dayEnd;
        this.automaticDifficultyEnabled = automaticDifficultyEnabled;
        this.notifyPlayersTitleEnabled = notifyPlayersTitleEnabled;
        this.notifyPlayersSoundEnabled = notifyPlayersSoundEnabled;
        this.automaticDifficultyDay = automaticDifficultyDay;
        this.automaticDifficultyNight = automaticDifficultyNight;
        this.notifyDayChat = langFile.getNotifyDayChat();
        this.notifyDayTitle = langFile.getNotifyDayTitle();
        this.notifyDaySubtitle = langFile.getNotifyDaySubtitle();
        this.notifyNightChat = langFile.getNotifyNightChat();
        this.notifyNightTitle = langFile.getNotifyNightTitle();
        this.notifyNightSubtitle = langFile.getNotifyNightSubtitle();
        this.notifyPlayersSoundDay = notifyPlayersSoundDay;
        this.notifyPlayersSoundNight = notifyPlayersSoundNight;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
        this.soundNightVolume = soundNightVolume;
        this.soundDayVolume = soundDayVolume;
        this.notifyPlayersChatDayNightStarts = notifyPlayersChatDayNightStarts;
        this.world = world;
    }

    @Override
    public void run() {
        if (checkTime(world)) {
            handleNight(world);
        } else {
            handleDay(world);
        }
        verifyPvpStatus(world);
    }

    private void handleNight(World world) {
        if (!nightWorlds.contains(world)) {
            nightWorlds.add(world);
            notifyPlayers(world, true);
            LoggingUtils.sendInfoMessage("[DayNightPvP] It's night in '" + world.getName() + "'");
        }
        dayWorlds.remove(world);
    }

    private void handleDay(World world) {
        if (!dayWorlds.contains(world)) {
            dayWorlds.add(world);
            notifyPlayers(world, false);
            LoggingUtils.sendInfoMessage("[DayNightPvP] It's day in '" + world.getName() + "'");
        }
        nightWorlds.remove(world);
    }

    public void verifyPvpStatus(World world) {
        if (!world.getPVP()) {
            LoggingUtils.sendWarningMessage("[DayNightPvP] Warning! Another plugin forced PvP to be disabled in the world '" + world.getName() + "' , trying to resolve...");
        }
    }

    public boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        boolean isNight = currentWorldTime >= dayEnd;

        if (isNight) {
            if (!nightWorlds.contains(world)) {
                handleNight(world);
            }
        } else {
            if (!dayWorlds.contains(world)) {
                handleDay(world);
            }
        }

        return isNight;
    }

    private void notifyPlayers(World world, boolean isNight) {
        if (automaticDifficultyEnabled) {
            world.setDifficulty(isNight ? automaticDifficultyNight : automaticDifficultyDay);
        }
        if (notifyPlayersChatDayNightStarts) {
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
                    isNight ? notifyPlayersSoundNight : notifyPlayersSoundDay,
                    isNight ? soundNightVolume : soundDayVolume);
        }
    }

}