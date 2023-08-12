package com.needkg.daynightpvp.utils;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigUtils {

    public static String getValue(String path) {
        return ConfigManager.configFileConfig.getString(path);
    }

    public static boolean getBoolean(String path) {
        return ConfigManager.configFileConfig.getBoolean(path);
    }

    public static List<String> getList() {
        return ConfigManager.configFileConfig.getStringList("worlds");
    }

    public static void saveConfig() {
        try {
            ConfigManager.configFileConfig.save(ConfigManager.configFile);
        } catch (Exception ignore) {

        }
    }

    public static void addWorldToList(String world) {
        List<String> worldList = ConfigManager.worldList;
        worldList.add(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        saveConfig();
    }

    public static void removeWorldToList(String world) {
        List<String> worldList = ConfigManager.worldList;
        worldList.remove(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        saveConfig();
    }

    public static void setWorlds(List<String> list) {
        ConfigManager.configFileConfig.set("worlds", list);
        saveConfig();
    }

    public static void setValue(FileConfiguration file,String path, String value) {
        file.set(path, value);
    }
}
