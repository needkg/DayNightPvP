package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.LanguageAccess;
import me.needkg.daynightpvp.configuration.enums.Message;

public class MessageManager {
    private final LanguageAccess access;

    public MessageManager(LanguageAccess access) {
        this.access = access;
    }

    public String getMessage(Message type) {
        return access.getMessage(type.getPath());
    }
} 