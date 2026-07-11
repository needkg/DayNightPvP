package me.needkg.daynightpvp.feature.config.providers;

import me.needkg.daynightpvp.feature.config.interfaces.ConfigProvider;
import me.needkg.daynightpvp.feature.config.models.MessagesConfig;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;

public class MessagesConfigProvider implements ConfigProvider<MessagesConfig> {

    private final ResourceFile resourceFile;
    private MessagesConfig messages;

    public MessagesConfigProvider(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Override
    public void init() {
        this.messages = new MessagesConfig(
                resourceFile.configuration().getString("messages.prefix", "&6DayNightPvP &8»"),
                resourceFile.configuration().getString("messages.noPermission", "You don't have permission to use this command."),
                resourceFile.configuration().getString("messages.commandUsage", "Usage: %usage%"));
    }

    @Override
    public void reload() {
        init();
    }

    public MessagesConfig get() {
        return messages;
    }

}
