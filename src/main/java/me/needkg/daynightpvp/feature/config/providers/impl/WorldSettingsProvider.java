package me.needkg.daynightpvp.feature.config.providers.impl;

import me.needkg.daynightpvp.feature.config.providers.ConfigProvider;
import me.needkg.daynightpvp.feature.config.providers.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.providers.models.WorldSettings;

public class WorldSettingsProvider implements ConfigProvider<WorldSettings> {

    private final ResourceFile resourceFile;
    private WorldSettings settings;
    private String worldName;

    public WorldSettingsProvider(ResourceFile resourceFile, String worldName) {
        this.resourceFile = resourceFile;
        this.worldName = worldName;
    }

    @Override
    public void init() {
        this.settings = new WorldSettings(
                resourceFile.configuration().getBoolean("worlds." + worldName + ".enabled", true));
    }

    @Override
    public void reload() {
        init();
    }

    public WorldSettings get() {
        return settings;
    }

}
