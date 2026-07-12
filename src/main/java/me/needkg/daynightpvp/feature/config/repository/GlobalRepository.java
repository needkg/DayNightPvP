package me.needkg.daynightpvp.feature.config.repository;

import me.needkg.daynightpvp.feature.config.repository.models.GlobalSettings;
import me.needkg.daynightpvp.feature.config.repository.models.ResourceFile;

public class GlobalRepository {

    private final ResourceFile resourceFile;

    public GlobalRepository(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    public GlobalSettings findGlobalSettings() {
        return new GlobalSettings(
                resourceFile.configuration().getString("language", "en"));
    }

}
