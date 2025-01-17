package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validators.LanguageValidator;

public class PlaceholderMessages {
    private final LanguageValidator validator;

    public PlaceholderMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    public String getPvpEnabledMessage() {
        return validator.getMessage("placeholders.pvp.enabled");
    }

    public String getPvpDisabledMessage() {
        return validator.getMessage("placeholders.pvp.disabled");
    }
} 