package com.needkg.daynightpvp.config;

import java.io.File;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ResourceLoaderYaml implements ResourceLoader {

    private final FileConfiguration configuration;

    public ResourceLoaderYaml(File yamlFile) {
        configuration = YamlConfiguration.loadConfiguration(yamlFile);
    }

    @Override
    public <T> T getValue(Class<T> type, String path) {
        return configuration.getObject(path, type);
    }

    @Override
    public <T> T getValue(Class<T> type, String path, T defaultValue) {
        return configuration.getObject(path, type, defaultValue);
    }

    @Override
    public <T> T getValue(Class<T> type, String path, ResourceLoader defaultResourceLoader) {
        return configuration.getObject(path, type, defaultResourceLoader.getValue(type, path));
    }

    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

}
