package com.needkg.daynightpvp.config;

import org.bukkit.Sound;

public class ConfigManager {

    public static String version;
    public static Boolean updateChecker;
    public static String lang;
    public static String[] worlds;
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
        griefPreventionPvpProtection = Boolean.parseBoolean(getValue("compatibility.griefpreventionPvpProtection"));
        enablePlaceholders = Boolean.parseBoolean(getValue("compatibility.enablePlaceholders"));
    }

    private String getValue(String path) {
        return StartupFiles.configFileConfig.getString(path);
    }

    private String[] getWorldList() {
        return getValue("worlds")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .split(" ");
    }

}