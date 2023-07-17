package com.needkg.daynightpvp.config;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration configFileConfig;


    public static String version;
    public static Boolean updateChecker;
    public static String lang;
    public static List<String> worlds;
    public static List<String> listWorld;
    public static int dayEnd;
    public static String dayDifficulty;
    public static String nightDifficulty;
    public static Boolean dayEnabled;
    public static Sound daySound;
    public static Float dayVolume;
    public static Float dayPitch;
    public static Boolean nightEnabled;
    public static Sound nightSound;
    public static Float nightVolume;
    public static Float nightPitch;
    public static Boolean pvpAlert;
    public static Boolean warnPvpControl;
    public static Boolean griefPreventionPvpProtection;
    public static Boolean enablePlaceholders;

    public void updateConfigs() {
        version = getValue("fileVersion");
        updateChecker = Boolean.parseBoolean(getValue("updateChecker"));
        lang = getValue("lang");
        worlds = getWorldList();
        dayEnd = Integer.parseInt(getValue("pvp.dayEnd"));
        dayDifficulty = getValue("pvp.dayDifficulty");
        nightDifficulty = getValue("pvp.nightDifficulty");
        dayEnabled = Boolean.parseBoolean(getValue("playSound.day.enabled"));
        daySound = Sound.valueOf(getValue("playSound.day.sound"));
        dayVolume = Float.valueOf(getValue("playSound.day.volume"));
        dayPitch = Float.valueOf(getValue("playSound.day.pitch"));
        nightEnabled = Boolean.parseBoolean(getValue("playSound.night.enabled"));
        nightSound = Sound.valueOf(getValue("playSound.night.sound"));
        nightVolume = Float.valueOf(getValue("playSound.night.volume"));
        nightPitch = Float.valueOf(getValue("playSound.night.pitch"));
        pvpAlert = Boolean.parseBoolean(getValue("messages.pvpAlert"));
        warnPvpControl = Boolean.parseBoolean(getValue("messages.warnPvpControl"));
        griefPreventionPvpProtection = Boolean.parseBoolean(getValue("compatibility.griefPreventionPvpProtection"));
        enablePlaceholders = Boolean.parseBoolean(getValue("compatibility.placeholdersApi"));

        listWorld = getListWorld();
    }

    private String getValue(String path) {
        return configFileConfig.getString(path);
    }

    private List<String> getWorldList() {
        return configFileConfig.getStringList("worlds");
    }

    private List<String> getListWorld() {
        return Collections.singletonList(getValue("worlds"));
    }

    public void saveConfig() {
        try {
            configFileConfig.save(configFile);
        } catch (Exception e) {

        }
    }

}
