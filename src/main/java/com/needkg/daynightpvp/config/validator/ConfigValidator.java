package com.needkg.daynightpvp.config.validator;

import com.needkg.daynightpvp.config.ConfigManager;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import com.needkg.daynightpvp.utils.LoggingUtils;

public class ConfigValidator {
    private final ConfigManager configManager;

    public ConfigValidator(ConfigManager configManager) {
        this.configManager = configManager;
    }

    private void resetValueToDefault(String path, Object value) {
        configManager.setValue(path, value);
        String configPath = path.replace(".", "/");
        LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
    }

    public int getInt(String path, Integer defaultValue, Integer minValue, Integer maxValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
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

    public float getFloat(String path, Float defaultValue, Float minValue, Float maxValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
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

    public Difficulty getDifficulty(String path, Difficulty defaultValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
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

    public Sound getSound(String path, Sound defaultValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
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

    public String getString(String path, String defaultValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
            String configPath = path.replace(".", "/");
            LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
            return defaultValue;
        }

        return configValue;
    }

    public boolean getBoolean(String path, Boolean defaultValue) {
        String configValue = configManager.getFileContent().getString(path);

        if (configValue == null) {
            configManager.resetFile();
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
} 