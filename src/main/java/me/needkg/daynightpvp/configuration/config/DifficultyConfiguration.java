package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;
import org.bukkit.Difficulty;

public class DifficultyConfiguration {
    private final ConfigurationValidator validator;

    public DifficultyConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
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

}
