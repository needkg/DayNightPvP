package me.needkg.daynightpvp.configuration.settings;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;

public class GeneralConfiguration {
    private final ConfigurationValidator validator;

    public GeneralConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public boolean getUpdateChecker() {
        return validator.getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return validator.getString("language", "en-US");
    }
} 