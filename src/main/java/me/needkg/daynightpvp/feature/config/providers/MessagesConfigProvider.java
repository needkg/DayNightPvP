package me.needkg.daynightpvp.feature.config.providers;

import me.needkg.daynightpvp.feature.config.interfaces.ConfigProvider;
import me.needkg.daynightpvp.feature.config.models.MessagesConfig;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;

public class MessagesConfigProvider implements ConfigProvider<MessagesConfig> {

    private final ResourceFile resourceFile;
    private MessagesConfig messages;

    public MessagesConfigProvider(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
        load();
    }

    private void load() {
        this.messages = new MessagesConfig(
                resourceFile.configuration().getString("messages.prefix"),
                resourceFile.configuration().getString("messages.noPermission"),
                resourceFile.configuration().getString("messages.commandUsage"));
    }

    public MessagesConfig get() {
        return messages;
    }

    @Override
    public void reload() {
        load();
    }

}
