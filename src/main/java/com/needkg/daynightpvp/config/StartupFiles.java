package com.needkg.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class StartupFiles {

    public static void startConfigFile(JavaPlugin plugin) {
        ConfigManager.configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!ConfigManager.configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        ConfigManager.configFileConfig = YamlConfiguration.loadConfiguration(ConfigManager.configFile);
    }

    public static void startLangsFile(JavaPlugin plugin) {
        for (String fileName : FilesManager.langFiles) {
            if (!new File(plugin.getDataFolder(), fileName).exists()) {
                plugin.saveResource(fileName, false);
            }
        }
    }

    public static FileConfiguration loadConfigFile(JavaPlugin plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

}
