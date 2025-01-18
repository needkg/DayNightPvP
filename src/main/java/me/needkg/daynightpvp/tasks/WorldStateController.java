package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.configuration.config.DifficultyConfiguration;
import me.needkg.daynightpvp.configuration.config.NotificationConfiguration;
import me.needkg.daynightpvp.configuration.config.PvpConfiguration;
import me.needkg.daynightpvp.configuration.message.NotificationMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.utils.LoggingUtil;
import me.needkg.daynightpvp.utils.PlayerUtil;
import org.bukkit.World;

import java.util.concurrent.CopyOnWriteArrayList;

public class WorldStateController implements Runnable {

    public static final CopyOnWriteArrayList<World> dayWorlds = new CopyOnWriteArrayList<>();
    public static final CopyOnWriteArrayList<World> nightWorlds = new CopyOnWriteArrayList<>();
    
    private final World world;
    private final String worldName;
    private final PvpConfiguration pvpConfiguration;
    private final DifficultyConfiguration difficultyConfiguration;
    private final NotificationConfiguration notificationConfiguration;
    private final NotificationMessages notificationMessages;
    private final long pvpDayEnd;

    public WorldStateController(World world, String worldName) {
        this.world = world;
        this.worldName = worldName;
        DependencyContainer container = DependencyContainer.getInstance();
        pvpConfiguration = container.getConfigurationContainer().getPvpConfiguration();
        difficultyConfiguration = container.getConfigurationContainer().getDifficultyConfiguration();
        notificationConfiguration = container.getConfigurationContainer().getNotificationConfiguration();
        notificationMessages = container.getMessageContainer().getNotifications();
        this.pvpDayEnd = pvpConfiguration.getPvpAutomaticDayEnd(worldName);
    }

    @Override
    public void run() {
        boolean isNight = isNightTime(world.getTime());

        if (isNight) {
            handleNight();
        } else {
            handleDay();
        }
        verifyPvpStatus();
    }

    private boolean isNightTime(long time) {
        return time >= pvpDayEnd;
    }

    private void handleNight() {
        if (!nightWorlds.contains(world)) {
            nightWorlds.add(world);
            dayWorlds.remove(world);
            notifyPlayers(true);
            LoggingUtil.sendDebugMessage("World '" + worldName + "' entered night period");
            LoggingUtil.sendVerboseMessage("World '" + worldName + "' details: Difficulty=" + world.getDifficulty() + ", PvP=" + world.getPVP() + ", Players Online=" + world.getPlayers().size() + " | Time: " + world.getTime() + "ticks");
        }
    }

    private void handleDay() {
        if (!dayWorlds.contains(world)) {
            dayWorlds.add(world);
            nightWorlds.remove(world);
            notifyPlayers(false);
            LoggingUtil.sendDebugMessage("World '" + worldName + "' entered day period");
            LoggingUtil.sendVerboseMessage("World '" + worldName + "' details: Difficulty=" + world.getDifficulty() + ", PvP=" + world.getPVP() + ", Players Online=" + world.getPlayers().size() + " | Time: " + world.getTime() + "ticks");
        }
    }

    private void verifyPvpStatus() {
        if (!world.getPVP()) {
            LoggingUtil.sendWarningMessage("[DayNightPvP] Warning! Another plugin is preventing automatic PvP from working in the world '" + worldName + "', attempting to resolve...");
            world.setPVP(true);
        }
    }

    private void notifyPlayers(boolean isNight) {
        if (difficultyConfiguration.getDifficultyEnabled(worldName)) {
            world.setDifficulty(isNight ? difficultyConfiguration.getDifficultyNight(worldName) : difficultyConfiguration.getDifficultyDay(worldName));
        }
        
        if (notificationConfiguration.getNotificationsChatDayNightChangeEnabled(worldName)) {
            PlayerUtil.sendMessageToAllPlayers(world, isNight ? notificationMessages.getNightChatMessage() : notificationMessages.getDayChatMessage());
        }
        
        if (notificationConfiguration.getNotificationsTitleEnabled(worldName)) {
            PlayerUtil.sendTitleToAllPlayers(world,
                    isNight ? notificationMessages.getNightTitle() : notificationMessages.getDayTitle(),
                    isNight ? notificationMessages.getNightSubtitle() : notificationMessages.getDaySubtitle(),
                    notificationConfiguration.getNotificationsTitleFadeIn(worldName),
                    notificationConfiguration.getNotificationsTitleStay(worldName),
                    notificationConfiguration.getNotificationsTitleFadeOut(worldName));
        }
        
        if (notificationConfiguration.getNotificationsSoundEnabled(worldName)) {
            PlayerUtil.playSoundToAllPlayers(world,
                    isNight ? notificationConfiguration.getNotificationsSoundNightType(worldName) : notificationConfiguration.getNotificationsSoundDayType(worldName),
                    isNight ? notificationConfiguration.getNotificationsSoundNightVolume(worldName) : notificationConfiguration.getNotificationsSoundDayVolume(worldName));
        }
    }
}