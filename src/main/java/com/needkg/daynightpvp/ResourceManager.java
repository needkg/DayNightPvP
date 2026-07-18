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

    private final static String CONFIG_YML = "config.yml";
    private final static String WORLDS = "worlds.yml";
    private final static String DEFAULT_LANG_YML = "lang/en.yml";

    private final JavaPlugin plugin;

    public ResourceManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ResourceLoader initializeConfigYml() {
        return loadYaml(plugin, Path.of(CONFIG_YML), false, null);
    }

    public ResourceLoader initializeLanguageYml(String lang) {
        Path langPath = Path.of("lang", lang + ".yml");

        final var defaultLang = new ResourceLoaderYaml(new InputStreamReader(plugin.getResource(DEFAULT_LANG_YML)));

        return loadYaml(plugin, langPath, false, defaultLang);

    }

    public ResourceLoader initializeWorldsYml() {
        return loadYaml(plugin, Path.of(WORLDS), false, null);

    }

    private ResourceLoader loadYaml(
            JavaPlugin plugin,
            Path resourcePath,
            boolean reset,
            ResourceLoader defaultResourceLoader) {

        File file = new File(plugin.getDataFolder(), resourcePath.toString());

        if (!file.exists() || reset) {
            plugin.saveResource(resourcePath.toString(), reset);
        }


        return new ResourceLoaderYaml(FileUtils.openReader(plugin.getDataPath().resolve(resourcePath)),
                defaultResourceLoader);

    }

}