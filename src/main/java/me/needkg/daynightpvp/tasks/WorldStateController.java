package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.utils.LoggingUtil;
import me.needkg.daynightpvp.utils.PlayerUtil;
import org.bukkit.World;

import java.util.concurrent.CopyOnWriteArrayList;

public class WorldStateController implements Runnable {

    public static final CopyOnWriteArrayList<World> dayWorlds = new CopyOnWriteArrayList<>();
    public static final CopyOnWriteArrayList<World> nightWorlds = new CopyOnWriteArrayList<>();
    
    private final World world;
    private final String worldName;
    private final WorldConfigurationManager worldConfigurationManager;
    private final MessageManager messageManager;
    private final long pvpDayEnd;

    public WorldStateController(World world, String worldName) {
        this.world = world;
        this.worldName = worldName;
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldConfigurationManager = container.getWorldConfigurationManager();
        this.messageManager = container.getMessageManager();
        this.pvpDayEnd = worldConfigurationManager.getPvpAutomaticDayEnd(worldName);
    }

    @Override
    public void run() {
        boolean isNight = isNightTime(world.getTime());

        if (isNight) {
            handleNight();
        } else {
            handleDay();
        }
        validateAndUpdatePvpState();
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

    private void validateAndUpdatePvpState() {
        if (!world.getPVP()) {
            LoggingUtil.sendWarningMessage("[DayNightPvP] Warning! Another plugin is preventing automatic PvP from working in the world '" + worldName + "', attempting to resolve...");
            world.setPVP(true);
        }
    }

    private void notifyPlayers(boolean isNight) {
        if (worldConfigurationManager.isDifficultyEnabled(worldName)) {
            world.setDifficulty(isNight ? worldConfigurationManager.getDifficultyNight(worldName) : worldConfigurationManager.getDifficultyDay(worldName));
        }
        
        if (worldConfigurationManager.isNotificationsChatDayNightChange(worldName)) {
            PlayerUtil.sendMessageToAllPlayers(world, isNight ? messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_NIGHT_CHAT) : messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_DAY_CHAT));
        }
        
        if (worldConfigurationManager.isNotificationsTitleEnabled(worldName)) {
            PlayerUtil.sendTitleToAllPlayers(world,
                    isNight ? messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_NIGHT_TITLE) : messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_DAY_TITLE),
                    isNight ? messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_NIGHT_SUBTITLE) : messageManager.getMessage(MessageType.NOTIFICATION_COMBAT_DAY_SUBTITLE),
                    worldConfigurationManager.getNotificationsTitleFadeIn(worldName),
                    worldConfigurationManager.getNotificationsTitleStay(worldName),
                    worldConfigurationManager.getNotificationsTitleFadeOut(worldName));
        }
        
        if (worldConfigurationManager.isNotificationsSoundEnabled(worldName)) {
            PlayerUtil.playSoundToAllPlayers(world,
                    isNight ? worldConfigurationManager.getNotificationsSoundNightType(worldName) : worldConfigurationManager.getNotificationsSoundDayType(worldName),
                    isNight ? worldConfigurationManager.getNotificationsSoundNightVolume(worldName) : worldConfigurationManager.getNotificationsSoundDayVolume(worldName));
        }
    }
}