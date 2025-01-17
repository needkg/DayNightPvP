package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;

public class DayNightDurationConfiguration {
    private final ConfigurationValidator validator;

    public DayNightDurationConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
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

}
