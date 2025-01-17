package me.needkg.daynightpvp.task;

import me.needkg.daynightpvp.configuration.message.NotificationMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.util.LoggingUtil;
import me.needkg.daynightpvp.util.PlayerUtil;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldStateController implements Runnable {

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
    private final NotificationMessages notificationMessages;

    public WorldStateController(
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
        DependencyContainer container = DependencyContainer.getInstance();
        notificationMessages = container.getMessageContainer().getNotifications();

        this.dayEnd = dayEnd;
        this.automaticDifficultyEnabled = automaticDifficultyEnabled;
        this.notifyPlayersTitleEnabled = notifyPlayersTitleEnabled;
        this.notifyPlayersSoundEnabled = notifyPlayersSoundEnabled;
        this.automaticDifficultyDay = automaticDifficultyDay;
        this.automaticDifficultyNight = automaticDifficultyNight;
        this.notifyDayChat = notificationMessages.getDayChatMessage();
        this.notifyDayTitle = notificationMessages.getDayTitle();
        this.notifyDaySubtitle = notificationMessages.getDaySubtitle();
        this.notifyNightChat = notificationMessages.getNightChatMessage();
        this.notifyNightTitle = notificationMessages.getNightTitle();
        this.notifyNightSubtitle = notificationMessages.getNightSubtitle();
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
            LoggingUtil.sendInfoMessage("[DayNightPvP] It's night in '" + world.getName() + "'");
        }
        dayWorlds.remove(world);
    }

    private void handleDay(World world) {
        if (!dayWorlds.contains(world)) {
            dayWorlds.add(world);
            notifyPlayers(world, false);
            LoggingUtil.sendInfoMessage("[DayNightPvP] It's day in '" + world.getName() + "'");
        }
        nightWorlds.remove(world);
    }

    public void verifyPvpStatus(World world) {
        if (!world.getPVP()) {
            LoggingUtil.sendWarningMessage("[DayNightPvP] Warning! Another plugin is preventing automatic PvP from working in the world '" + world.getName() + "', attempting to resolve...");
            world.setPVP(true);
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
            PlayerUtil.sendMessageToAllPlayers(world, isNight ? notifyNightChat : notifyDayChat);
        }
        if (notifyPlayersTitleEnabled) {
            PlayerUtil.sendTitleToAllPlayers(world,
                    isNight ? notifyNightTitle : notifyDayTitle,
                    isNight ? notifyNightSubtitle : notifyDaySubtitle,
                    fadeIn, stay, fadeOut);
        }
        if (notifyPlayersSoundEnabled) {
            PlayerUtil.playSoundToAllPlayers(world,
                    isNight ? notifyPlayersSoundNight : notifyPlayersSoundDay,
                    isNight ? soundNightVolume : soundDayVolume);
        }
    }

}