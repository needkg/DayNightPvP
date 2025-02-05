package me.needkg.daynightpvp.tasks.runnable;

import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.tasks.enums.WorldState;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.logging.Logger;
import me.needkg.daynightpvp.utils.player.PlayerNotifier;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;

public class WorldStateController implements Runnable {

    private final World world;
    private final String worldName;
    private final WorldStateManager worldStateManager;
    private final int dayEnd;
    private final Difficulty nightDifficulty;
    private final Difficulty dayDifficulty;
    private final boolean isDifficultyEnabled;
    private final boolean isNotificationsSoundEnabled;
    private final boolean isNotificationsTitleEnabled;
    private final boolean isNotificationsChatDayNightChange;
    private final Sound nightSound;
    private final Sound daySound;
    private final float nightSoundVolume;
    private final float daySoundVolume;
    private final String dayTitle;
    private final String nightTitle;
    private final String daySubtitle;
    private final String nightSubtitle;
    private final int dayTitleFadeIn;
    private final int dayTitleStay;
    private final int dayTitleFadeOut;
    private final String nightChat;
    private final String dayChat;


    public WorldStateController(World world, String worldName, WorldConfigurationManager worldConfigurationManager, MessageManager messageManager, WorldStateManager worldStateManager) {
        this.world = world;
        this.worldName = worldName;
        this.worldStateManager = worldStateManager;
        this.dayEnd = worldConfigurationManager.getPvpAutomaticDayEnd(worldName);
        this.nightDifficulty = worldConfigurationManager.getDifficultyNight(worldName);
        this.dayDifficulty = worldConfigurationManager.getDifficultyDay(worldName);
        this.isDifficultyEnabled = worldConfigurationManager.isDifficultyEnabled(worldName);
        this.isNotificationsSoundEnabled = worldConfigurationManager.isNotificationsSoundEnabled(worldName);
        this.isNotificationsTitleEnabled = worldConfigurationManager.isNotificationsTitleEnabled(worldName);
        this.isNotificationsChatDayNightChange = worldConfigurationManager.isNotificationsChatDayNightChange(worldName);
        this.nightSound = worldConfigurationManager.getNotificationsSoundNightType(worldName);
        this.daySound = worldConfigurationManager.getNotificationsSoundDayType(worldName);
        this.nightSoundVolume = worldConfigurationManager.getNotificationsSoundNightVolume(worldName);
        this.daySoundVolume = worldConfigurationManager.getNotificationsSoundDayVolume(worldName);
        this.dayTitle = messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_TITLE);
        this.nightTitle = messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_TITLE);
        this.daySubtitle = messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_SUBTITLE);
        this.nightSubtitle = messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_SUBTITLE);
        this.dayTitleFadeIn = worldConfigurationManager.getNotificationsTitleFadeIn(worldName);
        this.dayTitleStay = worldConfigurationManager.getNotificationsTitleStay(worldName);
        this.dayTitleFadeOut = worldConfigurationManager.getNotificationsTitleFadeOut(worldName);
        this.nightChat = messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_CHAT);
        this.dayChat = messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_CHAT);
    }

    @Override
    public void run() {
        handleWorldState();
        validateAndUpdatePvpState();

    }

    private void handleWorldState() {
        if (isNightWorld()) {
            handleNightWorld();
        } else {
            handleDayWorld();
        }
    }

    private boolean isNightWorld() {
        return world.getTime() >= dayEnd;
    }

    private void handleNightWorld() {
        if (worldStateManager.getWorldState(world) == WorldState.DAY) {
            worldStateManager.setWorldState(world, WorldState.NIGHT);
            notifyPlayers(true);
            Logger.debug("World '" + worldName + "' entered night period");
            Logger.verbose("World '" + worldName + "' details: Difficulty=" + world.getDifficulty() + ", PvP=" + world.getPVP() + ", Players Online=" + world.getPlayers().size() + " | Time: " + world.getTime() + "ticks");
        }
    }

    private void handleDayWorld() {
        if (worldStateManager.getWorldState(world) == WorldState.NIGHT) {
            worldStateManager.setWorldState(world, WorldState.DAY);
            notifyPlayers(false);
            Logger.debug("World '" + worldName + "' entered day period");
            Logger.verbose("World '" + worldName + "' details: Difficulty=" + world.getDifficulty() + ", PvP=" + world.getPVP() + ", Players Online=" + world.getPlayers().size() + " | Time: " + world.getTime() + "ticks");
        }
    }

    private void validateAndUpdatePvpState() {
        if (!world.getPVP()) {
            Logger.warning("[DayNightPvP] Warning! Another plugin is preventing automatic PvP from working in the world '" + worldName + "', attempting to resolve...");
            world.setPVP(true);
        }
    }

    private void notifyPlayers(boolean isNight) {
        setDifficulty(isNight);

        notifyPlayersChat(isNight);
        notifyPlayersTitle(isNight);
        notifyPlayersSound(isNight);
    }

    private void setDifficulty(boolean isNight) {
        if (isDifficultyEnabled) {
            world.setDifficulty(isNight ? nightDifficulty : dayDifficulty);
        }
    }

    private void notifyPlayersSound(boolean isNight) {
        if (isNotificationsSoundEnabled) {
            PlayerNotifier.broadcastSound(world,
                    isNight ? nightSound : daySound,
                    isNight ? nightSoundVolume : daySoundVolume);
        }
    }

    private void notifyPlayersTitle(boolean isNight) {
        if (isNotificationsTitleEnabled) {
            PlayerNotifier.broadcastTitle(world,
                    isNight ? nightTitle : dayTitle,
                    isNight ? nightSubtitle : daySubtitle,
                    dayTitleFadeIn,
                    dayTitleStay,
                    dayTitleFadeOut);
        }
    }

    private void notifyPlayersChat(boolean isNight) {
        if (isNotificationsChatDayNightChange) {
            PlayerNotifier.broadcastMessage(world, isNight ? nightChat : dayChat);
        }
    }


}
