package me.needkg.daynightpvp.feature.config;

import me.needkg.daynightpvp.feature.config.repository.models.ResourceFile;
import me.needkg.daynightpvp.shared.lifecycle.Loadable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ResourceLoader implements Loadable<ResourceFile, String>, Reloadable {

    private final JavaPlugin plugin;

    public ResourceLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
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
