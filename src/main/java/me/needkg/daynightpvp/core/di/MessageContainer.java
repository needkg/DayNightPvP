package me.needkg.daynightpvp.core.di;

import me.needkg.daynightpvp.configuration.message.*;
import me.needkg.daynightpvp.configuration.validator.LanguageValidator;

public class MessageContainer {
    private final NotificationMessages notifications;
    private final CombatMessages combat;
    private final WorldEditorMessages worldEditor;
    private final SystemMessages system;
    private final BossBarMessages bossBar;
    private final PlaceholderMessages placeholders;

    public MessageContainer(LanguageValidator validator) {
        this.notifications = new NotificationMessages(validator);
        this.combat = new CombatMessages(validator);
        this.worldEditor = new WorldEditorMessages(validator);
        this.system = new SystemMessages(validator);
        this.bossBar = new BossBarMessages(validator);
        this.placeholders = new PlaceholderMessages(validator);
    }

    public NotificationMessages getNotifications() {
        return notifications;
    }

    public CombatMessages getCombat() {
        return combat;
    }

    public WorldEditorMessages getWorldEditor() {
        return worldEditor;
    }

    public SystemMessages getSystem() {
        return system;
    }

    public BossBarMessages getBossBar() {
        return bossBar;
    }

    public PlaceholderMessages getPlaceholders() {
        return placeholders;
    }
} 