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

    public boolean getAutomaticPvpEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".automatic-pvp.enabled", true);
    }

    public int getAutomaticPvpDayEnd(String worldName) {
        return validator.getInt("worlds." + worldName + ".automatic-pvp.day-end", 12000, 1, 24000);
    }

    public boolean getTimeRemainingBossBarEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".boss-bar.time-remaining", false);
    }

    public boolean getAutomaticDifficultyEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".automatic-difficulty.enabled", false);
    }

    public Difficulty getAutomaticDifficultyDay(String worldName) {
        return validator.getDifficulty("worlds." + worldName + ".automatic-difficulty.day", Difficulty.NORMAL);
    }

    public Difficulty getAutomaticDifficultyNight(String worldName) {
        return validator.getDifficulty("worlds." + worldName + ".automatic-difficulty.night", Difficulty.HARD);
    }

    public boolean getPvpSettingsKeepInventoryWhenKilledByPlayersEnabled(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".pvp-settings.keep-inventory-when-killed-by-player", false);
        }
        return false;
    }

    public boolean getNotifyPlayersChatDayNightStartsEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notify-players.chat.day-night-starts", true);
    }

    public boolean getNotifyPlayersChatHitAnotherPlayerDuringDay(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notify-players.chat.hit-another-player-during-day", true);
    }

    public boolean getNotifyPlayersTitleEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notify-players.title.enabled", true);
    }

    public int getNotifyPlayersTitleFadeIn(String worldName) {
        return validator.getInt("worlds." + worldName + ".notify-players.title.fade-in", 20, 1, 86400);
    }

    public int getNotifyPlayersTitleStay(String worldName) {
        return validator.getInt("worlds." + worldName + ".notify-players.title.stay", 20, 1, 86400);
    }

    public int getNotifyPlayersTitleFadeOut(String worldName) {
        return validator.getInt("worlds." + worldName + ".notify-players.title.fade-out", 20, 1, 86400);
    }

    public boolean getNotifyPlayersSoundEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notify-players.sound.enabled", true);
    }

    public Sound getNotifyPlayersSoundDay(String worldName) {
        return validator.getSound("worlds." + worldName + ".notify-players.sound.day.sound", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public float getNotifyPlayersSoundDayVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notify-players.sound.day.volume", 1.0F, 0.0F, 1.0F);
    }

    public Sound getNotifyPlayersSoundNight(String worldName) {
        return validator.getSound("worlds." + worldName + ".notify-players.sound.night.sound", Sound.ENTITY_GHAST_AMBIENT);
    }

    public float getNotifyPlayersSoundNightVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notify-players.sound.night.volume", 1.0F, 0.0F, 1.0F);
    }

    public boolean getVaultLoseMoneyOnDeathEnabled(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".vault.lose-money-on-death.enabled", false);
        }
        return false;
    }

    public boolean getVaultLoseMoneyOnDeathOnlyAtNight(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".vault.lose-money-on-death.only-at-night", true);
    }

    public boolean getVaultLoseMoneyOnDeathKillerRewardMoney(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".vault.lose-money-on-death.killer-reward-money", true);
    }

    public boolean getGriefPreventionPvpInLand(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".grief-prevention.pvp-in-land", false);
        }
        return false;
    }
} 