package com.needkg.daynightpvp.config;

import java.io.File;
import java.io.Reader;
import java.util.Optional;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ResourceLoaderYaml implements ResourceLoader {

    private final FileConfiguration configuration;
    private final Optional<ResourceLoader> defaultResourceLoader;

    public ResourceLoaderYaml(Reader yamlReader) {
        configuration = YamlConfiguration.loadConfiguration(yamlReader);
        defaultResourceLoader = Optional.empty();
    }

    public ResourceLoaderYaml(Reader yamlReader, ResourceLoader defaultResourceLoader) {
        configuration = YamlConfiguration.loadConfiguration(yamlReader);
        this.defaultResourceLoader = Optional.ofNullable(defaultResourceLoader);
    }

    @Override
    public <T> T getValue(Class<T> type, String path) {

        final var value = configuration.getObject(path, type);
        if (value == null && defaultResourceLoader.isPresent()) {
            final var defaultValue = defaultResourceLoader.get().getValue(type, path);
            logDefaultValue(path, defaultValue);
            return defaultValue;
        }

        return value;
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
