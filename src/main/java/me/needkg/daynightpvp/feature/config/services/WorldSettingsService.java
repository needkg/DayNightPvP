package me.needkg.daynightpvp.feature.config.services;

import me.needkg.daynightpvp.feature.config.interfaces.ConfigProvider;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.models.WorldSettings;

public class WorldSettingsService implements ConfigProvider<WorldSettings> {

    private final ResourceFile resourceFile;
    private WorldSettings settings;
    private String worldName;

    public WorldSettingsService(ResourceFile resourceFile, String worldName) {
        this.resourceFile = resourceFile;
        this.worldName = worldName;
        load();
    }

    private void load() {
        this.settings = new WorldSettings(
                resourceFile.configuration().getBoolean("worlds." + worldName + ".enabled"));
    }

    public WorldSettings get() {
        return settings;
    }

    @Override
    public void reload() {
        load();
    }

}
