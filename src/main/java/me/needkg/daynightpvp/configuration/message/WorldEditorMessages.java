package me.needkg.daynightpvp.configuration.message;

import me.needkg.daynightpvp.configuration.validator.LanguageValidator;

public class WorldEditorMessages {
    private final LanguageValidator validator;

    public WorldEditorMessages(LanguageValidator validator) {
        this.validator = validator;
    }

    // Editor Title
    public String getTitle() {
        return validator.getMessage("worlds.editor.title");
    }

    // Settings Details
    public String getSettingDetailsTitle() {
        return validator.getMessage("worlds.editor.settings.details.title");
    }

    public String getSettingDescription() {
        return validator.getMessage("worlds.editor.settings.details.description");
    }

    public String getSettingCurrentValue() {
        return validator.getMessage("worlds.editor.settings.details.current_value");
    }

    public String getSettingType() {
        return validator.getMessage("worlds.editor.settings.details.type");
    }

    public String getSettingRange() {
        return validator.getMessage("worlds.editor.settings.details.range");
    }

    public String getSettingSuggestions() {
        return validator.getMessage("worlds.editor.settings.details.suggestions");
    }

    // Settings Feedback
    public String getInvalidSettingMessage() {
        return validator.getMessage("worlds.editor.feedback.settings.invalid_setting");
    }

    public String getInvalidValueMessage() {
        return validator.getMessage("worlds.editor.feedback.settings.invalid_value");
    }

    public String getSuccessMessage() {
        return validator.getMessage("worlds.editor.feedback.settings.success");
    }

    public String getSameValueMessage() {
        return validator.getMessage("worlds.editor.feedback.settings.same_value");
    }

    // World Management Feedback
    public String getWorldAddedMessage() {
        return validator.getMessage("worlds.editor.feedback.management.success.added");
    }

    public String getWorldDeletedMessage() {
        return validator.getMessage("worlds.editor.feedback.management.success.deleted");
    }

    public String getWorldNotConfiguredMessage() {
        return validator.getMessage("worlds.editor.feedback.management.errors.not_configured");
    }

    public String getWorldAlreadyExistsMessage() {
        return validator.getMessage("worlds.editor.feedback.management.errors.already_exists");
    }

    public String getWorldNotExistsMessage() {
        return validator.getMessage("worlds.editor.feedback.management.errors.not_exists");
    }
} 