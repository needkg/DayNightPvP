package com.needkg.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartupFiles {

    public static File configFile;
    public static FileConfiguration configFileConfig;
    public static FileConfiguration currentLangFile;
    public static List<String> langFiles = new ArrayList<>();

    public StartupFiles() {
        langFiles = Arrays.asList("lang/en-US.yml", "lang/pt-BR.yml", "lang/es-ES.yml");
    }

    public void startConfigFile(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        configFileConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    public void startLangsFile(JavaPlugin plugin) {
        for (String fileName : langFiles) {
            if (!new File(plugin.getDataFolder(), fileName).exists()) {
                plugin.saveResource(fileName, false);
            }
        }
    }

    public void selectLangFile(JavaPlugin plugin) {
        String pathLangFile = "lang/" + ConfigManager.lang + ".yml";
        currentLangFile = loadConfigFile(plugin, pathLangFile);
    }

    public static FileConfiguration loadConfigFile(JavaPlugin plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

}
