package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.LanguageAccess;
import me.needkg.daynightpvp.configuration.type.MessageType;

public class MessageManager {
    private final LanguageAccess reader;

    public MessageManager(LanguageAccess reader) {
        this.reader = reader;
    }

    public String getMessage(MessageType type) {
        return reader.getMessage(type.getPath());
    }
} 