package me.needkg.daynightpvp.feature.config.settings;

import me.needkg.daynightpvp.feature.config.ResourceFile;

public class PluginSettingsGateway {

    private final ResourceFile resourceFile;

    public PluginSettingsGateway(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public PluginSettings findGlobalSettings() {
        return new PluginSettings(
                resourceFile.configuration().getString("language", "en"));
    }

}
