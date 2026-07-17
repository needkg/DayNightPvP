package com.needkg.daynightpvp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.config.ResourceLoaderYaml;
import com.needkg.daynightpvp.util.FileUtils;

public class ResourceManager {

    private final JavaPlugin plugin;

    public ResourceManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ResourceLoader initializeConfigYml() {
        return loadYaml(plugin, Path.of("config.yml"), false, null);
    }

    public ResourceLoader initializeLanguageYml(String lang) {
        Path langPath = Path.of("lang", lang + ".yml");

        // loadYaml(plugin, langPath, false, getFromResources(langPath));

        // return new ResourceLoaderYaml(new
        // InputStreamReader(plugin.getResource(langPath.toString())));
        return loadYaml(plugin, langPath, false, getDefaultYml(Path.of("lang", "en.yml")));

    }

    // private ResourceLoader getFromResources(Path resourcePath) {
    //     return new ResourceLoaderYaml(FileUtils.openReader(resourcePath));
    // }

    public ResourceLoader getDefaultYml(Path resourcePath) {
        return new ResourceLoaderYaml(new InputStreamReader(plugin.getResource(resourcePath.toString())));
    }

    public ResourceLoader initializeWorldsYml() {
        // loadYaml(plugin, Path.of("worlds.yml"), false, null);

        return null;

    }

    private ResourceLoader loadYaml(
            JavaPlugin plugin,
            Path resourcePath,
            boolean reset,
            ResourceLoader defaultResourceLoader) {

        new InputStreamReader(plugin.getResource(resourcePath.toString()));

        plugin.saveResource(resourcePath.toString(), reset);

        return new ResourceLoaderYaml(FileUtils.openReader(resourcePath),
                defaultResourceLoader);

    }

    // private ResourceLoader load(JavaPlugin plugin, String resourcePath, boolean
    // reset) {

    // File resourceFile = new File(plugin.getDataFolder(), resourcePath);

    // if (!resourceFile.exists()) {
    // plugin.saveResource(resourcePath, reset);
    // }

    // return new ResourceLoaderYaml(resourceFile);

    // }

}