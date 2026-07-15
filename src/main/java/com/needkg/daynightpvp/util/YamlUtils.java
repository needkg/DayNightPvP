package com.needkg.daynightpvp.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class YamlUtils {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    // private static final FileConfiguration configuration = YamlConfiguration.loadConfiguration(null);

    private YamlUtils() {
        // Private constructor to prevent instantiation
    }

    // public static <T> T getValue(Class<T> valueType, String worldName, String path) {
    //     try {

    //         // configuration.getObject(path, valueType,)

    //         final String finalPath = worldName + "." + path;

    //         // File file = new File(plugin.getDataFolder(), "worlds.yml");

    //         // if (!file.exists()) {
    //         // plugin.saveResource(filePath, false);
    //         // }

    //         // FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    //         // //configuration.getString(worldName + "." + path);
    //         // configuration.getBoolean(worldName + "." + "pvp.enabled");
    //         // configuration.getLong(worldName + "." + "pvp.day-end");
    //         // .getConfigurationSection("").getKeys(false)); // retorna

    //         final var yamlFile = new File(path + "/" + worldName + ".yml");

    //         return mapper.readValue(
    //                 yamlFile,
    //                 valueType);
    //     } catch (IOException e) {
    //         // e.printStackTrace(); //TODO logar erro como aviso
    //         return null;
    //     }
    // }

    private static <T> T readYaml(File yamlFile, Class<T> valueType) {
        try {
            return mapper.readValue(
                    yamlFile,
                    valueType);
        } catch (IOException e) {
            // e.printStackTrace(); //TODO logar erro como aviso
            return null;
        }
    }

}
