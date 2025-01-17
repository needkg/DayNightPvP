package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validator.LanguageValidator;

public class BossBarMessages {
    private final LanguageValidator validator;

    public BossBarMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    public String getUntilSunsetMessage() {
        return validator.getMessage("bossbar.time.until_sunset");
    }

    public String getUntilSunriseMessage() {
        return validator.getMessage("bossbar.time.until_sunrise");
    }
} 