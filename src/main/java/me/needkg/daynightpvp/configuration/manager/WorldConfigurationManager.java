package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.emun.World;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

public class WorldConfigurationManager {
    private final ConfigurationAccess reader;

    public WorldConfigurationManager(ConfigurationAccess reader) {
        this.reader = reader;
    }

    // Bossbar Configuration
    public boolean isBossbarEnabled(String worldName) {
        String path = World.BOSSBAR.getWorldPath(worldName);
        return World.BOSSBAR.getBossbarEnabled(worldName, path, reader);
    }

    // Day/Night Duration Configuration
    public boolean isDayNightDurationEnabled(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationEnabled(worldName, path, reader);
    }

    public int getDayNightDurationDayDuration(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationDayDuration(worldName, path, reader);
    }

    public int getDayNightDurationNightDuration(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationNightDuration(worldName, path, reader);
    }

    // Integration Configuration
    public boolean isIntegrationsVaultLoseMoneyEnabled(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyEnabled(worldName, path, reader);
    }

    public boolean isIntegrationsVaultLoseMoneyOnlyAtNight(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyOnlyAtNight(worldName, path, reader);
    }

    public boolean isIntegrationsVaultLoseMoneyRewardKiller(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyRewardKiller(worldName, path, reader);
    }

    public boolean isIntegrationsGriefPreventionPvpInClaims(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsGriefPreventionPvpInClaims(worldName, path, reader);
    }

    // PvP Configuration
    public boolean isPvpAutomaticEnabled(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpAutomaticEnabled(worldName, path, reader);
    }

    public int getPvpAutomaticDayEnd(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpAutomaticDayEnd(worldName, path, reader);
    }

    public boolean isPvpKeepInventoryEnabled(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpKeepInventoryEnabled(worldName, path, reader);
    }

    public boolean isPvpKeepInventoryKeepExp(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpKeepInventoryKeepExp(worldName, path, reader);
    }

    // Difficulty Configuration
    public boolean isDifficultyEnabled(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyEnabled(worldName, path, reader);
    }

    public Difficulty getDifficultyDay(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyDay(worldName, path, reader);
    }

    public Difficulty getDifficultyNight(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyNight(worldName, path, reader);
    }

    // Notification Configuration
    public boolean isNotificationsChatDayNightChange(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsChatDayNightChange(worldName, path, reader);
    }

    public boolean isNotificationsChatNoPvpWarn(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsChatNoPvpWarn(worldName, path, reader);
    }

    public boolean isNotificationsTitleEnabled(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleEnabled(worldName, path, reader);
    }

    public int getNotificationsTitleFadeIn(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleFadeIn(worldName, path, reader);
    }

    public int getNotificationsTitleStay(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleStay(worldName, path, reader);
    }

    public int getNotificationsTitleFadeOut(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleFadeOut(worldName, path, reader);
    }

    public boolean isNotificationsSoundEnabled(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundEnabled(worldName, path, reader);
    }

    public Sound getNotificationsSoundDayType(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundDayType(worldName, path, reader);
    }

    public float getNotificationsSoundDayVolume(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundDayVolume(worldName, path, reader);
    }

    public Sound getNotificationsSoundNightType(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundNightType(worldName, path, reader);
    }

    public float getNotificationsSoundNightVolume(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundNightVolume(worldName, path, reader);
    }
} 