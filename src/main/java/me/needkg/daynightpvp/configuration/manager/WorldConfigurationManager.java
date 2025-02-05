package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.enums.World;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

public class WorldConfigurationManager {
    private final ConfigurationAccess access;

    public WorldConfigurationManager(ConfigurationAccess access) {
        this.access = access;
    }

    // Bossbar Configuration
    public boolean isBossbarEnabled(String worldName) {
        String path = World.BOSSBAR.getWorldPath(worldName);
        return World.BOSSBAR.getBossbarEnabled(worldName, path, access);
    }

    // Day/Night Duration Configuration
    public boolean isDayNightDurationEnabled(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationEnabled(worldName, path, access);
    }

    public int getDayNightDurationDayDuration(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationDayDuration(worldName, path, access);
    }

    public int getDayNightDurationNightDuration(String worldName) {
        String path = World.DAY_NIGHT_DURATION.getWorldPath(worldName);
        return World.DAY_NIGHT_DURATION.getDayNightDurationNightDuration(worldName, path, access);
    }

    // Integration Configuration
    public boolean isIntegrationsVaultLoseMoneyEnabled(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyEnabled(worldName, path, access);
    }

    public boolean isIntegrationsVaultLoseMoneyOnlyAtNight(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyOnlyAtNight(worldName, path, access);
    }

    public boolean isIntegrationsVaultLoseMoneyRewardKiller(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsVaultLoseMoneyRewardKiller(worldName, path, access);
    }

    public boolean isIntegrationsGriefPreventionPvpInClaims(String worldName) {
        String path = World.INTEGRATION.getWorldPath(worldName);
        return World.INTEGRATION.getIntegrationsGriefPreventionPvpInClaims(worldName, path, access);
    }

    // PvP Configuration
    public boolean isPvpAutomaticEnabled(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpAutomaticEnabled(worldName, path, access);
    }

    public int getPvpAutomaticDayEnd(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpAutomaticDayEnd(worldName, path, access);
    }

    public boolean isPvpKeepInventoryEnabled(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpKeepInventoryEnabled(worldName, path, access);
    }

    public boolean isPvpKeepInventoryKeepExp(String worldName) {
        String path = World.PVP.getWorldPath(worldName);
        return World.PVP.getPvpKeepInventoryKeepExp(worldName, path, access);
    }

    // Difficulty Configuration
    public boolean isDifficultyEnabled(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyEnabled(worldName, path, access);
    }

    public Difficulty getDifficultyDay(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyDay(worldName, path, access);
    }

    public Difficulty getDifficultyNight(String worldName) {
        String path = World.DIFFICULTY.getWorldPath(worldName);
        return World.DIFFICULTY.getDifficultyNight(worldName, path, access);
    }

    // Notification Configuration
    public boolean isNotificationsChatDayNightChange(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsChatDayNightChange(worldName, path, access);
    }

    public boolean isNotificationsChatNoPvpWarn(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsChatNoPvpWarn(worldName, path, access);
    }

    public boolean isNotificationsTitleEnabled(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleEnabled(worldName, path, access);
    }

    public int getNotificationsTitleFadeIn(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleFadeIn(worldName, path, access);
    }

    public int getNotificationsTitleStay(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleStay(worldName, path, access);
    }

    public int getNotificationsTitleFadeOut(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsTitleFadeOut(worldName, path, access);
    }

    public boolean isNotificationsSoundEnabled(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundEnabled(worldName, path, access);
    }

    public Sound getNotificationsSoundDayType(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundDayType(worldName, path, access);
    }

    public float getNotificationsSoundDayVolume(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundDayVolume(worldName, path, access);
    }

    public Sound getNotificationsSoundNightType(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundNightType(worldName, path, access);
    }

    public float getNotificationsSoundNightVolume(String worldName) {
        String path = World.NOTIFICATION.getWorldPath(worldName);
        return World.NOTIFICATION.getNotificationsSoundNightVolume(worldName, path, access);
    }
} 