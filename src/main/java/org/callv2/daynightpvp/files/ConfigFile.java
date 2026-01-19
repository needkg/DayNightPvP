package org.callv2.daynightpvp.files;

import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.utils.LoggingUtils;
import org.callv2.daynightpvp.utils.WorldUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigFile {

    public static File fileLocation;
    public static FileConfiguration fileContent;

    public void createFile() {
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml");
        if (!fileLocation.exists()) {
            DayNightPvP.getInstance().saveResource("config.yml", false);
        }
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        verifyFileVersion();
    }

    private void verifyFileVersion() {
        int latestFileVersion = 20;
        if (latestFileVersion != getVersion()) {
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The 'config.yml' file was outdated and has been renamed to 'config.yml.old'.";
                LoggingUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the 'config.yml' file.";
                LoggingUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        }
    }

    public boolean contains(String text) {
        return fileContent.contains(text);
    }

    public void setValue(String path, Object value) {
        fileContent.set(path, value);
        saveConfig();
    }

    public void saveConfig() {
        try {
            fileContent.save(fileLocation);
        } catch (Exception e) {
            LoggingUtils.sendWarningMessage("[DayNightPvP] Error saving configuration file, resetting...");
            resetFile();
        }
    }

    public void resetFile() {
        DayNightPvP.getInstance().saveResource("config.yml", true);
    }

    private void resetValueToDefault(String path, Object value) {
        setValue(path, value);
        String configPath = path.replace(".", "/");
        LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
    }

    private int getInt(String path, Integer defaultValue, Integer minValue, Integer maxValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        try {
            int intValue = Integer.parseInt(configValue);

            if (intValue >= minValue && intValue <= maxValue) {
                return intValue;
            } else {
                resetValueToDefault(path, defaultValue);
                return defaultValue;
            }
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    private float getFloat(String path, Float defaultValue, Float minValue, Float maxValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        try {
            float floatValue = Float.parseFloat(configValue);

            if (floatValue >= minValue && floatValue <= maxValue) {
                return floatValue;
            } else {
                resetValueToDefault(path, defaultValue);
                return defaultValue;
            }
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    private Difficulty getDifficulty(String path, Difficulty defaultValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        try {
            return Difficulty.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    private Sound getSound(String path, Sound defaultValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        try {
            return Sound.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    private String getString(String path, String defaultValue) {
        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        return configValue;
    }

    private boolean getBoolean(String path, Boolean defaultValue) {
        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(configValue);
        } else {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    public Set<String> getWorldNames() {
        return fileContent.getConfigurationSection("worlds").getKeys(false);
    }

    public List<String> getValidWorldNames() {
        Set<String> worldNames = getWorldNames();
        List<String> validWorldNames = new ArrayList<>();
        for (String worldName : worldNames) {
            if (WorldUtils.isWorldValid(worldName)) {
                validWorldNames.add(worldName);
            }
        }
        return validWorldNames;
    }

    public int getVersion() {
        return fileContent.getInt("version");
    }

    public boolean getUpdateChecker() {
        return getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return getString("language", "en-US");
    }

    public boolean getDayNightDurationEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".day-night-duration.enabled", false);
    }

    public int getDayNightDurationDayDuration(String worldName) {
        return getInt("worlds." + worldName + ".day-night-duration.day-duration", 600, 1, 86400);
    }

    public int getDayNightDurationNightDuration(String worldName) {
        return getInt("worlds." + worldName + ".day-night-duration.night-duration", 600, 1, 86400);
    }

    public boolean getAutomaticPvpEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".automatic-pvp.enabled", true);
    }

    public int getAutomaticPvpDayEnd(String worldName) {
        return getInt("worlds." + worldName + ".automatic-pvp.day-end", 12000, 1, 24000);
    }

    public boolean getTimeRemainingBossBarEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".boss-bar.time-remaining", false);
    }

    public boolean getAutomaticDifficultyEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".automatic-difficulty.enabled", false);
    }

    public Difficulty getAutomaticDifficultyDay(String worldName) {
        return getDifficulty("worlds." + worldName + ".automatic-difficulty.day", Difficulty.NORMAL);
    }

    public Difficulty getAutomaticDifficultyNight(String worldName) {
        return getDifficulty("worlds." + worldName + ".automatic-difficulty.night", Difficulty.HARD);
    }

    public boolean getPvpSettingsKeepInventoryWhenKilledByPlayersEnabled(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return getBoolean("worlds." + worldName + ".pvp-settings.keep-inventory-when-killed-by-player", false);
        } else {
            return false;
        }
    }

    public boolean getNotifyPlayersChatDayNightStartsEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".notify-players.chat.day-night-starts", true);
    }

    public boolean getNotifyPlayersChatHitAnotherPlayerDuringDay(String worldName) {
        return getBoolean("worlds." + worldName + ".notify-players.chat.hit-another-player-during-day", true);
    }

    public boolean getNotifyPlayersTitleEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".notify-players.title.enabled", true);
    }

    public int getNotifyPlayersTitleFadeIn(String worldName) {
        return getInt("worlds." + worldName + ".notify-players.title.fade-in", 20, 1, 86400);
    }

    public int getNotifyPlayersTitleStay(String worldName) {
        return getInt("worlds." + worldName + ".notify-players.title.stay", 20, 1, 86400);
    }

    public int getNotifyPlayersTitleFadeOut(String worldName) {
        return getInt("worlds." + worldName + ".notify-players.title.fade-out", 20, 1, 86400);
    }

    public boolean getNotifyPlayersSoundEnabled(String worldName) {
        return getBoolean("worlds." + worldName + ".notify-players.sound.enabled", true);
    }

    public Sound getNotifyPlayersSoundDay(String worldName) {
        return getSound("worlds." + worldName + ".notify-players.sound.day.sound", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public float getNotifyPlayersSoundDayVolume(String worldName) {
        return getFloat("worlds." + worldName + ".notify-players.sound.day.volume", 1.0F, 0.0F, 1.0F);
    }

    public Sound getNotifyPlayersSoundNight(String worldName) {
        return getSound("worlds." + worldName + ".notify-players.sound.night.sound", Sound.ENTITY_GHAST_AMBIENT);
    }

    public float getNotifyPlayersSoundNightVolume(String worldName) {
        return getFloat("worlds." + worldName + ".notify-players.sound.night.volume", 1.0F, 0.0F, 1.0F);
    }

    public boolean getVaultLoseMoneyOnDeathEnabled(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return getBoolean("worlds." + worldName + ".vault.lose-money-on-death.enabled", false);
        } else {
            return false;
        }
    }

    public boolean getVaultLoseMoneyOnDeathOnlyAtNight(String worldName) {
        return getBoolean("worlds." + worldName + ".vault.lose-money-on-death.only-at-night", true);
    }

    public boolean getVaultLoseMoneyOnDeathKillerRewardMoney(String worldName) {
        return getBoolean("worlds." + worldName + ".vault.lose-money-on-death.killer-reward-money", true);
    }

    public boolean getGriefPreventionPvpInLand(String worldName) {
        if (getWorldNames().contains(worldName)) {
            return getBoolean("worlds." + worldName + ".claims.pvp-in-land", false);
        } else {
            return false;
        }
    }

}