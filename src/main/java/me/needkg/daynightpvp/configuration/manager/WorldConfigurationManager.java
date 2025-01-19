package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.type.WorldConfigurationType;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

public class WorldConfigurationManager {
    private final ConfigurationAccess reader;

    public WorldConfigurationManager(ConfigurationAccess reader) {
        this.reader = reader;
    }

    // Bossbar Configuration
    public boolean isBossbarEnabled(String worldName) {
        String path = WorldConfigurationType.BOSSBAR.getWorldPath(worldName);
        return WorldConfigurationType.BOSSBAR.getBossbarEnabled(worldName, path, reader);
    }

    // Day/Night Duration Configuration
    public boolean isDayNightDurationEnabled(String worldName) {
        String path = WorldConfigurationType.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return WorldConfigurationType.DAY_NIGHT_DURATION.getDayNightDurationEnabled(worldName, path, reader);
    }

    public int getDayNightDurationDayDuration(String worldName) {
        String path = WorldConfigurationType.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return WorldConfigurationType.DAY_NIGHT_DURATION.getDayNightDurationDayDuration(worldName, path, reader);
    }

    public int getDayNightDurationNightDuration(String worldName) {
        String path = WorldConfigurationType.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return WorldConfigurationType.DAY_NIGHT_DURATION.getDayNightDurationNightDuration(worldName, path, reader);
    }

    // Integration Configuration
    public boolean isIntegrationsVaultLoseMoneyEnabled(String worldName) {
        String path = WorldConfigurationType.INTEGRATION.getWorldPath(worldName);
        return WorldConfigurationType.INTEGRATION.getIntegrationsVaultLoseMoneyEnabled(worldName, path, reader);
    }

    public boolean isIntegrationsVaultLoseMoneyOnlyAtNight(String worldName) {
        String path = WorldConfigurationType.INTEGRATION.getWorldPath(worldName);
        return WorldConfigurationType.INTEGRATION.getIntegrationsVaultLoseMoneyOnlyAtNight(worldName, path, reader);
    }

    public boolean isIntegrationsVaultLoseMoneyRewardKiller(String worldName) {
        String path = WorldConfigurationType.INTEGRATION.getWorldPath(worldName);
        return WorldConfigurationType.INTEGRATION.getIntegrationsVaultLoseMoneyRewardKiller(worldName, path, reader);
    }

    public boolean isIntegrationsGriefPreventionPvpInClaims(String worldName) {
        String path = WorldConfigurationType.INTEGRATION.getWorldPath(worldName);
        return WorldConfigurationType.INTEGRATION.getIntegrationsGriefPreventionPvpInClaims(worldName, path, reader);
    }

    // PvP Configuration
    public boolean isPvpAutomaticEnabled(String worldName) {
        String path = WorldConfigurationType.PVP.getWorldPath(worldName);
        return WorldConfigurationType.PVP.getPvpAutomaticEnabled(worldName, path, reader);
    }

    public int getPvpAutomaticDayEnd(String worldName) {
        String path = WorldConfigurationType.PVP.getWorldPath(worldName);
        return WorldConfigurationType.PVP.getPvpAutomaticDayEnd(worldName, path, reader);
    }

    public boolean isPvpKeepInventoryEnabled(String worldName) {
        String path = WorldConfigurationType.PVP.getWorldPath(worldName);
        return WorldConfigurationType.PVP.getPvpKeepInventoryEnabled(worldName, path, reader);
    }

    public boolean isPvpKeepInventoryKeepExp(String worldName) {
        String path = WorldConfigurationType.PVP.getWorldPath(worldName);
        return WorldConfigurationType.PVP.getPvpKeepInventoryKeepExp(worldName, path, reader);
    }

    // Difficulty Configuration
    public boolean isDifficultyEnabled(String worldName) {
        String path = WorldConfigurationType.DIFFICULTY.getWorldPath(worldName);
        return WorldConfigurationType.DIFFICULTY.getDifficultyEnabled(worldName, path, reader);
    }

    public Difficulty getDifficultyDay(String worldName) {
        String path = WorldConfigurationType.DIFFICULTY.getWorldPath(worldName);
        return WorldConfigurationType.DIFFICULTY.getDifficultyDay(worldName, path, reader);
    }

    public Difficulty getDifficultyNight(String worldName) {
        String path = WorldConfigurationType.DIFFICULTY.getWorldPath(worldName);
        return WorldConfigurationType.DIFFICULTY.getDifficultyNight(worldName, path, reader);
    }

    // Notification Configuration
    public boolean isNotificationsChatDayNightChange(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsChatDayNightChange(worldName, path, reader);
    }

    public boolean isNotificationsChatNoPvpWarn(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsChatNoPvpWarn(worldName, path, reader);
    }

    public boolean isNotificationsTitleEnabled(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsTitleEnabled(worldName, path, reader);
    }

    public int getNotificationsTitleFadeIn(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsTitleFadeIn(worldName, path, reader);
    }

    public int getNotificationsTitleStay(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsTitleStay(worldName, path, reader);
    }

    public int getNotificationsTitleFadeOut(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsTitleFadeOut(worldName, path, reader);
    }

    public boolean isNotificationsSoundEnabled(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsSoundEnabled(worldName, path, reader);
    }

    public Sound getNotificationsSoundDayType(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsSoundDayType(worldName, path, reader);
    }

    public float getNotificationsSoundDayVolume(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsSoundDayVolume(worldName, path, reader);
    }

    public Sound getNotificationsSoundNightType(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsSoundNightType(worldName, path, reader);
    }

    public float getNotificationsSoundNightVolume(String worldName) {
        String path = WorldConfigurationType.NOTIFICATION.getWorldPath(worldName);
        return WorldConfigurationType.NOTIFICATION.getNotificationsSoundNightVolume(worldName, path, reader);
    }
} 