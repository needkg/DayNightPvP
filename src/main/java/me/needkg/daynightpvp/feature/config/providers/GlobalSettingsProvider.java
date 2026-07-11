package me.needkg.daynightpvp.feature.config.providers;

import me.needkg.daynightpvp.feature.config.interfaces.ConfigProvider;
import me.needkg.daynightpvp.feature.config.models.GlobalSettings;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;

public class GlobalSettingsProvider implements ConfigProvider<GlobalSettings> {

    private final ResourceFile resourceFile;
    private GlobalSettings settings;

    public GlobalSettingsProvider(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
        load();
    }

    private void load() {
        this.settings = new GlobalSettings(
                resourceFile.configuration().getString("language"),
                resourceFile.configuration().getConfigurationSection("worlds").getKeys(false));
    }

    public GlobalSettings get() {
        return settings;
    }

    @Override
    public void reload() {
        load();
    }

}
