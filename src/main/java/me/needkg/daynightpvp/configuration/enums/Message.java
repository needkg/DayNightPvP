package me.needkg.daynightpvp.configuration.enums;

public enum Message {
    BOSSBAR_UNTIL_SUNSET("bossbar.time.until_sunset"),
    BOSSBAR_UNTIL_SUNRISE("bossbar.time.until_sunrise"),
    COMBAT_MONEY_LOST("combat.economy.lost"),
    COMBAT_MONEY_WON("combat.economy.won"),
    NOTIFICATION_COMBAT_DAY_CHAT("notifications.combat.day.chat"),
    NOTIFICATION_COMBAT_DAY_TITLE("notifications.combat.day.title"),
    NOTIFICATION_COMBAT_DAY_SUBTITLE("notifications.combat.day.subtitle"),
    NOTIFICATION_COMBAT_NIGHT_CHAT("notifications.combat.night.chat"),
    NOTIFICATION_COMBAT_NIGHT_TITLE("notifications.combat.night.title"),
    NOTIFICATION_COMBAT_NIGHT_SUBTITLE("notifications.combat.night.subtitle"),
    COMBAT_RESTRICTION_DAYTIME("notifications.combat_restrictions.daytime_disabled"),
    COMBAT_RESTRICTION_PLAYER_IMMUNE("notifications.combat_restrictions.player_immune"),
    COMBAT_RESTRICTION_SELF_IMMUNE("notifications.combat_restrictions.self_immune"),
    WORLD_EDITOR_TITLE("worlds.editor.title"),
    WORLD_EDITOR_SETTING_DETAILS_TITLE("worlds.editor.settings.details.title"),
    WORLD_EDITOR_SETTING_DETAILS_DESCRIPTION("worlds.editor.settings.details.description"),
    WORLD_EDITOR_SETTING_DETAILS_CURRENT("worlds.editor.settings.details.current_value"),
    WORLD_EDITOR_SETTING_DETAILS_TYPE("worlds.editor.settings.details.type"),
    WORLD_EDITOR_SETTING_DETAILS_RANGE("worlds.editor.settings.details.range"),
    WORLD_EDITOR_SETTING_DETAILS_SUGGESTIONS("worlds.editor.settings.details.suggestions"),
    WORLD_EDITOR_FEEDBACK_INVALID_SETTING("worlds.editor.feedback.settings.invalid_setting"),
    WORLD_EDITOR_FEEDBACK_INVALID_VALUE("worlds.editor.feedback.settings.invalid_value"),
    WORLD_EDITOR_FEEDBACK_SUCCESS("worlds.editor.feedback.settings.success"),
    WORLD_EDITOR_FEEDBACK_SAME_VALUE("worlds.editor.feedback.settings.same_value"),
    WORLD_EDITOR_MANAGEMENT_ADDED("worlds.editor.feedback.management.success.added"),
    WORLD_EDITOR_MANAGEMENT_DELETED("worlds.editor.feedback.management.success.deleted"),
    WORLD_EDITOR_ERROR_NOT_CONFIGURED("worlds.editor.feedback.management.errors.not_configured"),
    WORLD_EDITOR_ERROR_ALREADY_EXISTS("worlds.editor.feedback.management.errors.already_exists"),
    WORLD_EDITOR_ERROR_NOT_EXISTS("worlds.editor.feedback.management.errors.not_exists"),
    SYSTEM_COMMAND_INCORRECT("system.commands.incorrect"),
    SYSTEM_COMMAND_NOT_FOUND("system.commands.not_found"),
    SYSTEM_PERMISSION_DENIED("system.permissions.denied"),
    SYSTEM_LANGUAGE_ALREADY_IN_USE("system.language.already_in_use"),
    SYSTEM_LANGUAGE_CHANGED("system.language.changed"),
    SYSTEM_UPDATE_AVAILABLE("system.updates.available"),
    SYSTEM_UPDATE_CHECK_FAILED("system.updates.check_failed"),
    SYSTEM_UPDATE_CURRENT_VERSION("system.updates.current_version"),
    SYSTEM_UPDATE_LATEST_VERSION("system.updates.latest_version"),
    SYSTEM_UPDATE_CLICK_TO_UPDATE("system.updates.click_to_update"),
    SYSTEM_RELOAD_SUCCESS("system.reload.success"),
    SYSTEM_ERROR("system.error"),
    PLACEHOLDER_PVP_ENABLED("placeholders.pvp.enabled"),
    PLACEHOLDER_PVP_DISABLED("placeholders.pvp.disabled");

    private final String path;

    Message(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
} 