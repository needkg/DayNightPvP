package me.needkg.daynightpvp.config.settings;

import me.needkg.daynightpvp.config.validator.ConfigValidator;

public class GeneralSettings {
    private final ConfigValidator validator;

    public GeneralSettings(ConfigValidator validator) {
        this.validator = validator;
    }

    public boolean getUpdateChecker() {
        return validator.getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return validator.getString("language", "en-US");
    }
} 