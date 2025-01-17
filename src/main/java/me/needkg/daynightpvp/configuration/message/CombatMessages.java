package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validators.LanguageValidator;

public class CombatMessages {
    private final LanguageValidator validator;

    public CombatMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    public String getMoneyLostMessage() {
        return validator.getMessage("combat.economy.lost");
    }

    public String getMoneyWonMessage() {
        return validator.getMessage("combat.economy.won");
    }
} 