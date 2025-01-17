package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;

public class BossbarConfiguration {
    private final ConfigurationValidator validator;

    public BossbarConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public boolean getBossbarEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".boss-bar.enabled", false);
    }

}
