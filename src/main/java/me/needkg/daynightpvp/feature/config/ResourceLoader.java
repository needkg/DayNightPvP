package me.needkg.daynightpvp.feature.config;

import me.needkg.daynightpvp.shared.lifecycle.Loadable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;
import me.needkg.daynightpvp.shared.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ResourceLoader implements Loadable<ResourceFile, String>, Reloadable {

    private final JavaPlugin plugin;
    private final Logger logger;
    private static final String DEFAULT_FILE_PATH = "config.yml";

    public ResourceLoader(JavaPlugin plugin, Logger logger) {
        this.logger = logger;
        this.plugin = plugin;
    }

    @Override
    public ResourceFile load(String filePath) {

        logger.info("Loading '" + filePath + "'...");

        if (filePath.isEmpty()) {
            logger.warn("File path is empty. Using default file path: '" + DEFAULT_FILE_PATH + "'");
            filePath = DEFAULT_FILE_PATH;
        }

        File file = new File(plugin.getDataFolder(), filePath);

        if (!file.exists()) {
            logger.info("Resource file not found. Creating default file...");
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
