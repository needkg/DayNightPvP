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

        final var value = getValue(type, path);
        if (value == null) {
            logDefaultValue(path, defaultValue);
            return defaultValue;
        }

        return value;
    }

    @Override
    public <T> T getValue(Class<T> type, String path, ResourceLoader defaultResourceLoader) {
        return getValue(type, path, defaultResourceLoader.getValue(type, path));
    }

    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }

    private void logDefaultValue(String path, Object defaultValue) {

        // TODO implementar log de aviso para defaultValue usado, mas não encontrado no
        // logger.warn("It seems that the value for path '" + path + "' is missing or
        // invalid. Using default value: " + defaultValue + "consider checking your
        // configuration file.");
    }

}
