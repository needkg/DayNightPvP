package me.needkg.daynightpvp.feature.config.settings;

import me.needkg.daynightpvp.feature.config.ResourceFile;

public class MessagesSettingsGateway {

    private final ResourceFile resourceFile;

    public MessagesSettingsGateway(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public MessagesSettings findMessagesSettings() {
        return new MessagesSettings(
                resourceFile.configuration().getString("messages.prefix"),
                resourceFile.configuration().getString("messages.noPermission"),
                resourceFile.configuration().getString("messages.commandUsage"));
    }

}
