package me.needkg.daynightpvp.configuration.access;

import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.utils.LoggingUtil;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

import java.util.Set;

public class ConfigurationAccess {

    private final ConfigurationFile configurationFile;

    public ConfigurationAccess(ConfigurationFile configurationFile) {
        this.configurationFile = configurationFile;
    }

    private void restoreDefaultValue(String path, Object value) {
        configurationFile.setValue(path, value);
        notifyConfigReset(path);
    }

    private void notifyConfigReset(String path) {
        String configPath = path.replace(".", "/");
        LoggingUtil.sendWarningMessage("[DayNightPvP] The '" + configPath + "' configuration was not found and the file was reset.");
    }

    private String getConfigValue(String path, Object defaultValue) {
        String configValue = configurationFile.getFileContent().getString(path);
        if (configValue == null) {
            configurationFile.restoreDefaultFile();
            notifyConfigReset(path);
            return defaultValue.toString();
        }
        return configValue;
    }

    public Set<String> getConfigSection(String path) {
        return configurationFile.getFileContent().getConfigurationSection(path).getKeys(false);
    }

    public int getInt(String path, Integer defaultValue, Integer minValue, Integer maxValue) {
        String configValue = getConfigValue(path, defaultValue);

        try {
            int intValue = Integer.parseInt(configValue);
            if (isValueInRange(intValue, minValue, maxValue)) {
                return intValue;
            }
        } catch (Exception ignored) {
        }

        restoreDefaultValue(path, defaultValue);
        return defaultValue;
    }

    public float getFloat(String path, Float defaultValue, Float minValue, Float maxValue) {
        String configValue = getConfigValue(path, defaultValue);

        try {
            float floatValue = Float.parseFloat(configValue);
            if (isValueInRange(floatValue, minValue, maxValue)) {
                return floatValue;
            }
        } catch (Exception ignored) {
        }

        restoreDefaultValue(path, defaultValue);
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
            restoreDefaultValue(path, defaultValue.name());
            return defaultValue;
        }
    }

    public Sound getSound(String path, Sound defaultValue) {
        String configValue = getConfigValue(path, defaultValue);

        try {
            return Sound.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            restoreDefaultValue(path, defaultValue.name());
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

        restoreDefaultValue(path, defaultValue);
        return defaultValue;
    }
} 