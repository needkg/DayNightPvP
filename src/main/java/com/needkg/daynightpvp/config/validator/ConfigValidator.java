package com.needkg.daynightpvp.config.validator;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.LoggingUtils;

import java.util.Set;

import org.bukkit.Difficulty;
import org.bukkit.Sound;

public class ConfigValidator {
    
    private final ConfigManager configManager;

    public ConfigValidator(ConfigManager configManager) {
        this.configManager = configManager;
    }

    private void resetValueToDefault(String path, Object value) {
        configManager.setValue(path, value);
        notifyConfigReset(path);
    }

    private void notifyConfigReset(String path) {
        String configPath = path.replace(".", "/");
        LoggingUtils.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
    }

    private String getConfigValue(String path, Object defaultValue) {
        String configValue = configManager.getFileContent().getString(path);
        if (configValue == null) {
            configManager.resetFile();
            notifyConfigReset(path);
            return defaultValue.toString();
        }
        return configValue;
    }

    public Set<String> getConfigSection(String path) {
        return configManager.getFileContent().getConfigurationSection(path).getKeys(false);
    }

    public int getInt(String path, Integer defaultValue, Integer minValue, Integer maxValue) {
        String configValue = getConfigValue(path, defaultValue);
        
        try {
            int intValue = Integer.parseInt(configValue);
            if (isValueInRange(intValue, minValue, maxValue)) {
                return intValue;
            }
        } catch (Exception ignored) {}
        
        resetValueToDefault(path, defaultValue);
        return defaultValue;
    }

    public float getFloat(String path, Float defaultValue, Float minValue, Float maxValue) {
        String configValue = getConfigValue(path, defaultValue);
        
        try {
            float floatValue = Float.parseFloat(configValue);
            if (isValueInRange(floatValue, minValue, maxValue)) {
                return floatValue;
            }
        } catch (Exception ignored) {}
        
        resetValueToDefault(path, defaultValue);
        return defaultValue;
    }

    private <T extends Comparable<T>> boolean isValueInRange(T value, T min, T max) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    public Difficulty getDifficulty(String path, Difficulty defaultValue) {
        String configValue = getConfigValue(path, defaultValue);
        
        try {
            return Difficulty.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    public Sound getSound(String path, Sound defaultValue) {
        String configValue = getConfigValue(path, defaultValue);
        
        try {
            return Sound.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    public String getString(String path, String defaultValue) {
        return getConfigValue(path, defaultValue);
    }

    public boolean getBoolean(String path, Boolean defaultValue) {
        String configValue = getConfigValue(path, defaultValue);
        
        if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(configValue);
        }
        
        resetValueToDefault(path, defaultValue);
        return defaultValue;
    }
} 