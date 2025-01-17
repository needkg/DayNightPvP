package me.needkg.daynightpvp.configuration.settings;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;
import me.needkg.daynightpvp.util.WorldUtil;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WorldConfiguration {
    private final ConfigurationValidator validator;

    public WorldConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public Set<String> getWorldNames() {
        return validator.getConfigSection("worlds");
    }

    public List<String> getValidWorldNames() {
        Set<String> worldNames = getWorldNames();
        List<String> validWorldNames = new ArrayList<>();
        for (String worldName : worldNames) {
            if (WorldUtil.isWorldValid(worldName)) {
                validWorldNames.add(worldName);
            }
        }
        return validWorldNames;
    }

    public boolean getDayNightDurationEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".day-night-duration.enabled", false);
    }

    public int getDayNightDurationDayDuration(String worldName) {
        return validator.getInt("worlds." + worldName + ".day-night-duration.day-duration", 600, 1, 86400);
    }

    public int getDayNightDurationNightDuration(String worldName) {
        return validator.getInt("worlds." + worldName + ".day-night-duration.night-duration", 600, 1, 86400);
    }

    public boolean getPvpAutomaticEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".pvp.automatic.enabled", true);
    }

    public int getPvpAutomaticDayEnd(String worldName) {
        return validator.getInt("worlds." + worldName + ".pvp.automatic.day-end", 12000, 1, 24000);
    }

    public boolean getPvpKeepInventoryOnPvp(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".pvp.keep-inventory-on-pvp", false);
        }
        return false;
    }

    public boolean getBossbarEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".boss-bar.enabled", false);
    }

    public boolean getDifficultyEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".difficulty.enabled", false);
    }

    public Difficulty getDifficultyDay(String worldName) {
        return validator.getDifficulty("worlds." + worldName + ".difficulty.day", Difficulty.NORMAL);
    }

    public Difficulty getDifficultyNight(String worldName) {
        return validator.getDifficulty("worlds." + worldName + ".difficulty.night", Difficulty.HARD);
    }

    public boolean getNotificationsChatDayNightChangeEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.chat.day-night-change", true);
    }

    public boolean getNotificationsChatNoPvpWarn(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.chat.no-pvp-warn", true);
    }

    public boolean getNotificationsTitleEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.title.enabled", true);
    }

    public int getNotificationsTitleFadeIn(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.fade-in", 20, 1, 86400);
    }

    public int getNotificationsTitleStay(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.stay", 20, 1, 86400);
    }

    public int getNotificationsTitleFadeOut(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.fade-out", 20, 1, 86400);
    }

    public boolean getNotificationsSoundEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.sound.enabled", true);
    }

    public Sound getNotificationsSoundDayType(String worldName) {
        return validator.getSound("worlds." + worldName + ".notifications.sound.day.type", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public float getNotificationsSoundDayVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notifications.sound.day.volume", 1.0F, 0.0F, 1.0F);
    }

    public Sound getNotificationsSoundNightType(String worldName) {
        return validator.getSound("worlds." + worldName + ".notifications.sound.night.type", Sound.ENTITY_GHAST_AMBIENT);
    }

    public float getNotificationsSoundNightVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notifications.sound.night.volume", 1.0F, 0.0F, 1.0F);
    }

    public boolean getIntegrationsVaultLoseMoneyEnabled(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.enabled", false);
        }
        return false;
    }

    public boolean getIntegrationsVaultLoseMoneyOnlyAtNight(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.only-at-night", true);
    }

    public boolean getIntegrationsVaultLoseMoneyRewardKiller(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.reward-killer", true);
    }

    public boolean getIntegrationsGriefPreventionPvpInClaims(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".integrations.grief-prevention.pvp-in-claims", false);
        }
        return false;
    }
} 