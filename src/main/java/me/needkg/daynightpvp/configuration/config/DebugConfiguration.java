package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;

public class DebugConfiguration {
    private final ConfigurationValidator validator;

    public DebugConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public boolean isDebugEnabled() {
        return validator.getBoolean("debug.enabled", false);
    }

    public boolean isVerboseEnabled() {
        return validator.getBoolean("debug.verbose", false);
    }
}