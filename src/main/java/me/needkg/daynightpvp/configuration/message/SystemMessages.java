package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validator.LanguageValidator;

public class SystemMessages {
    private final LanguageValidator validator;

    public SystemMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    // Command Messages
    public String getIncorrectCommandMessage() {
        return validator.getMessage("system.commands.incorrect");
    }

    public String getCommandNotFoundMessage() {
        return validator.getMessage("system.commands.not_found");
    }

    // Permission Messages
    public String getPermissionDeniedMessage() {
        return validator.getMessage("system.permissions.denied");
    }

    // Language Messages
    public String getLanguageAlreadyInUseMessage() {
        return validator.getMessage("system.language.already_in_use");
    }

    public String getLanguageChangedMessage() {
        return validator.getMessage("system.language.changed");
    }

    // Update Messages
    public String getUpdateAvailableMessage() {
        return validator.getMessage("system.updates.available");
    }

    public String getUpdateCheckFailedMessage() {
        return validator.getMessage("system.updates.check_failed");
    }

    public String getCurrentVersionMessage() {
        return validator.getMessage("system.updates.current_version");
    }

    public String getLatestVersionMessage() {
        return validator.getMessage("system.updates.latest_version");
    }

    public String getClickToUpdateMessage() {
        return validator.getMessage("system.updates.click_to_update");
    }

    // Reload Messages
    public String getReloadSuccessMessage() {
        return validator.getMessage("system.reload.success");
    }

    public String getErrorMessage() {
        return validator.getMessage("system.error");
    }
} 