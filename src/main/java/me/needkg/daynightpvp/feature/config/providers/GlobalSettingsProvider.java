package me.needkg.daynightpvp.feature.config.providers;

import java.util.Collections;

import org.bukkit.configuration.ConfigurationSection;

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

        ConfigurationSection worldsSection = resourceFile.configuration().getConfigurationSection("worlds");

        this.settings = new GlobalSettings(
                resourceFile.configuration().getString("language", "en"),
                worldsSection != null ? worldsSection.getKeys(false) : Collections.emptySet());
    }

    public GlobalSettings get() {
        return settings;
    }

    @Override
    public void reload() {
        load();
    }

}
