package org.callvdois.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration configFileConfig;

    public String getString(String path) {
        return configFileConfig.getString(path);
    }

    public int getInt(String path) {
        return configFileConfig.getInt(path);
    }

    public boolean getBoolean(String path) {
        return configFileConfig.getBoolean(path);
    }

    public List<String> getList(String path) {
        return configFileConfig.getStringList(path);
    }

    public int getVersion() {
        return getInt("version");
    }

    public void saveConfig() {
        try {
            configFileConfig.save(configFile);
        } catch (Exception ignore) {

        }
    }

    public void addToList(String world, String path) {
        List<String> list = getList(path);
        list.add(world);
        configFileConfig.set(path, list);
        saveConfig();
    }

    public void removeFromList(String world, String path) {
        List<String> list = getList(path);
        list.remove(world);
        configFileConfig.set(path, list);
        saveConfig();
    }

    public void setValue(String path, String value) {
        configFileConfig.set(path, value);
    }

}
