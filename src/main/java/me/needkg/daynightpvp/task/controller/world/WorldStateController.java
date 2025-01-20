package me.needkg.daynightpvp.task.controller.world;

import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.util.logging.Logger;
import me.needkg.daynightpvp.util.player.PlayerNotifier;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class WorldStateController implements Runnable {

    public static final Set<World> dayWorlds = new HashSet<>();
    public static final Set<World> nightWorlds = new HashSet<>();

    private final World world;
    private final String worldName;
    private final WorldConfigurationManager worldConfigurationManager;
    private final MessageManager messageManager;
    private final long pvpDayEnd;

    public WorldStateController(World world, String worldName, WorldConfigurationManager worldConfigurationManager, MessageManager messageManager) {
        this.world = world;
        this.worldName = worldName;
        this.worldConfigurationManager = worldConfigurationManager;
        this.messageManager = messageManager;
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
            Logger.debug("World '" + worldName + "' entered night period");
            Logger.verbose("World '" + worldName + "' details: Difficulty=" + world.getDifficulty() + ", PvP=" + world.getPVP() + ", Players Online=" + world.getPlayers().size() + " | Time: " + world.getTime() + "ticks");
        }
    }

    private void handleDay() {
        if (!dayWorlds.contains(world)) {
            dayWorlds.add(world);
            nightWorlds.remove(world);
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
        if (worldConfigurationManager.isDifficultyEnabled(worldName)) {
            world.setDifficulty(isNight ? worldConfigurationManager.getDifficultyNight(worldName) : worldConfigurationManager.getDifficultyDay(worldName));
        }

        if (worldConfigurationManager.isNotificationsChatDayNightChange(worldName)) {
            PlayerNotifier.broadcastMessage(world, isNight ? messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_CHAT) : messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_CHAT));
        }

        if (worldConfigurationManager.isNotificationsTitleEnabled(worldName)) {
            PlayerNotifier.broadcastTitle(world,
                    isNight ? messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_TITLE) : messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_TITLE),
                    isNight ? messageManager.getMessage(Message.NOTIFICATION_COMBAT_NIGHT_SUBTITLE) : messageManager.getMessage(Message.NOTIFICATION_COMBAT_DAY_SUBTITLE),
                    worldConfigurationManager.getNotificationsTitleFadeIn(worldName),
                    worldConfigurationManager.getNotificationsTitleStay(worldName),
                    worldConfigurationManager.getNotificationsTitleFadeOut(worldName));
        }

        if (worldConfigurationManager.isNotificationsSoundEnabled(worldName)) {
            PlayerNotifier.broadcastSound(world,
                    isNight ? worldConfigurationManager.getNotificationsSoundNightType(worldName) : worldConfigurationManager.getNotificationsSoundDayType(worldName),
                    isNight ? worldConfigurationManager.getNotificationsSoundNightVolume(worldName) : worldConfigurationManager.getNotificationsSoundDayVolume(worldName));
        }
    }
}