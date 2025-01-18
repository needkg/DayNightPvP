package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validators.LanguageValidator;

public class NotificationMessages {
    private final LanguageValidator validator;

    public NotificationMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    // Combat Day Messages
    public String getDayChatMessage() {
        return validator.getMessage("notifications.combat.day.chat");
    }

    public String getDayTitle() {
        return validator.getMessage("notifications.combat.day.title");
    }

    public String getDaySubtitle() {
        return validator.getMessage("notifications.combat.day.subtitle");
    }

    // Combat Night Messages
    public String getNightChatMessage() {
        return validator.getMessage("notifications.combat.night.chat");
    }

    public String getNightTitle() {
        return validator.getMessage("notifications.combat.night.title");
    }

    public String getNightSubtitle() {
        return validator.getMessage("notifications.combat.night.subtitle");
    }

    // Combat Restrictions
    public String getDaytimeDisabled() {
        return validator.getMessage("notifications.combat_restrictions.daytime_disabled");
    }

    public String getPlayerImmune() {
        return validator.getMessage("notifications.combat_restrictions.player_immune");
    }

    public String getSelfImmune() {
        return validator.getMessage("notifications.combat_restrictions.self_immune");
    }
} 