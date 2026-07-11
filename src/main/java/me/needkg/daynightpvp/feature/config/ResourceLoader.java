package me.needkg.daynightpvp.feature.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;

public class ResourceLoader implements Reloadable {

    private final JavaPlugin plugin;

    public ResourceLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ResourceFile load(String filePath) {

        if (filePath.isEmpty()) {
            filePath = "config.yml";
        }

        File file = new File(plugin.getDataFolder(), filePath);

        if (!file.exists()) {
            plugin.saveResource(filePath, false);
        }

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        return new ResourceFile(file.toPath(), configuration);
    }

    @Override
    public void reload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reload'");
    }

}
