package com.needkg.daynightpvp;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.config.ResourceLoaderYaml;

public class ResourceManager {

    public ResourceManager() {

    }

    public ResourceLoaderYaml load(JavaPlugin plugin, String resourcePath, boolean reset) {

        File resourceFile = new File(plugin.getDataFolder(), resourcePath);

        if (!resourceFile.exists()) {
            plugin.saveResource(resourcePath, reset);
        }

        return new ResourceLoaderYaml(resourceFile);

    }

}