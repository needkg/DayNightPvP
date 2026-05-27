package me.needkg.daynightpvp.feature.config.loader;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.models.ResourceFile;

public class ConfigLoader {

    private final JavaPlugin plugin;

    public ConfigLoader(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    public ResourceFile initializeFile(String filePath) {

        File file = new File(plugin.getDataFolder(), filePath);

        if (!file.exists()) {
            plugin.saveResource(filePath, false);
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        return new ResourceFile(file.toPath(), configuration);
    }



}
