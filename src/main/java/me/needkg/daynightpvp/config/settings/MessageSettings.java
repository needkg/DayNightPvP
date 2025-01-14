package me.needkg.daynightpvp.config.settings;

import me.needkg.daynightpvp.config.validator.LangValidator;

public class MessageSettings {
    private final LangValidator validator;

    public MessageSettings(LangValidator validator) {
        this.validator = validator;
    }

    // Notificações
    public String getNotifyDayChat() {
        return validator.getMessage("notify-day-chat");
    }

    public String getNotifyNightChat() {
        return validator.getMessage("notify-night-chat");
    }

    public String getNotifyDayTitle() {
        return validator.getMessage("notify-day-title");
    }

    public String getNotifyDaySubtitle() {
        return validator.getMessage("notify-day-subtitle");
    }

    public String getNotifyNightTitle() {
        return validator.getMessage("notify-night-title");
    }

    public String getNotifyNightSubtitle() {
        return validator.getMessage("notify-night-subtitle");
    }

    public String getNotifyPvpDisabled() {
        return validator.getMessage("notify-pvp-disabled");
    }

    public String getNotifyPlayerImmune() {
        return validator.getMessage("notify-player-immune");
    }

    public String getNotifySelfImmune() {
        return validator.getMessage("notify-self-immune");
    }

    // Feedback
    public String getFeedbackUpdateAvailable() {
        return validator.getMessage("feedback-update-available");
    }

    public String getFeedbackUpdateCheckFailed() {
        return validator.getMessage("feedback-update-check-failed");
    }

    public String getFeedbackUpdateCurrentVersion() {
        return validator.getMessage("feedback-update-current-version");
    }

    public String getFeedbackUpdateLatestVersion() {
        return validator.getMessage("feedback-update-latest-version");
    }

    public String getFeedbackReloadPlugin() {
        return validator.getMessage("feedback-reload-plugin");
    }

    public String getFeedbackLoseMoney() {
        return validator.getMessage("feedback-lose-money");
    }

    public String getFeedbackWinMoney() {
        return validator.getMessage("feedback-win-money");
    }

    public String getFeedbackAddedWorld() {
        return validator.getMessage("feedback-added-world");
    }

    public String getFeedbackDeletedWorld() {
        return validator.getMessage("feedback-deleted-world");
    }

    public String getFeedbackWorldIsNotInSettings() {
        return validator.getMessage("feedback-world-is-not-in-settings");
    }

    public String getFeedbackWorldAlreadyExists() {
        return validator.getMessage("feedback-world-already-exists");
    }

    public String getFeedbackWorldDoesNotExist() {
        return validator.getMessage("feedback-world-does-not-exist");
    }

    public String getFeedbackIncorrectCommand() {
        return validator.getMessage("feedback-incorrect-command");
    }

    public String getFeedbackNonExistentCommand() {
        return validator.getMessage("feedback-non-existent-command");
    }

    public String getFeedbackBossbarSunset() {
        return validator.getMessage("feedback-boss-bar-sunset");
    }

    public String getFeedbackBossbarSunrise() {
        return validator.getMessage("feedback-boss-bar-sunrise");
    }

    public String getFeedbackError() {
        return validator.getMessage("feedback-error");
    }

    public String getFeedbackLangChanged() {
        return validator.getMessage("feedback-lang-changed");
    }

    // EditWorld Command Messages
    public String getFeedbackEditWorldInvalidSetting() {
        return validator.getMessage("feedback-editworld-invalid-setting");
    }

    public String getFeedbackEditWorldInvalidValue() {
        return validator.getMessage("feedback-editworld-invalid-value");
    }

    public String getFeedbackEditWorldSuccess() {
        return validator.getMessage("feedback-editworld-success");
    }

    // EditWorld Command UI
    public String getEditWorldTitle() {
        return validator.getMessage("feedback-editworld-title");
    }

    public String getEditWorldSettingDetailsTitle() {
        return validator.getMessage("feedback-editworld-setting-details-title");
    }

    public String getEditWorldSettingDescription() {
        return validator.getMessage("feedback-editworld-setting-description");
    }

    public String getEditWorldSettingCurrentValue() {
        return validator.getMessage("feedback-editworld-setting-current-value");
    }

    public String getEditWorldSettingType() {
        return validator.getMessage("feedback-editworld-setting-type");
    }

    public String getEditWorldSettingRange() {
        return validator.getMessage("feedback-editworld-setting-range");
    }

    public String getEditWorldSettingSuggestions() {
        return validator.getMessage("feedback-editworld-setting-suggestions");
    }

    public String getFeedbackEditWorldSameValue() {
        return validator.getMessage("feedback-editworld-same-value");
    }

    // Ações
    public String getActionUpdateFoundClick() {
        return validator.getMessage("action-update-found-click");
    }

    // Placeholder
    public String getPlaceholderPvpEnabled() {
        return validator.getMessage("placeholder-pvp-enabled");
    }

    public String getPlaceholderPvpDisabled() {
        return validator.getMessage("placeholder-pvp-disabled");
    }

    public String getFeedbackErrorNoPermission() {
        return validator.getMessage("feedback-error-no-permission");
    }

    public String getFeedbackErrorLanguageInUse() {
        return validator.getMessage("feedback-error-language-in-use");
    }
} 