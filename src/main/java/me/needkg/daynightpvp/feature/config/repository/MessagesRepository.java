package me.needkg.daynightpvp.feature.config.repository;

import me.needkg.daynightpvp.feature.config.models.MessagesSettings;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;

public class MessagesRepository {

    private final ResourceFile resourceFile;

    public MessagesRepository(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public MessagesSettings findMessagesSettings() {
        return new MessagesSettings(
                resourceFile.configuration().getString("messages.prefix", "&6DayNightPvP &8»"),
                resourceFile.configuration().getString("messages.noPermission", "You don't have permission to use this command."),
                resourceFile.configuration().getString("messages.commandUsage", "Usage: %usage%"));
    }

}
