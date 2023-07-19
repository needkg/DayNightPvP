package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration configFileConfig;

    //public static Boolean updateChecker;
    //public static String lang;
    //public static List<String> worlds;
    //public static int dayEnd;
    //public static String dayDifficulty;
    //public static String nightDifficulty;
    //public static Boolean dayEnabled;
    //public static Sound daySound;
    //public static Float dayVolume;
    //public static Float dayPitch;
    //public static Boolean nightEnabled;
    //public static Sound nightSound;
    //public static Float nightVolume;
    //public static Float nightPitch;
    //public static Boolean pvpAlert;
    //public static Boolean griefPreventionPvpProtection;
    //public static Boolean enablePlaceholders;
    //public static Boolean vaultEnabled;
    //public static Boolean loseMoneyEnabled;
    //public static Boolean loseMoneyOnlyNight;
    //public static Boolean loseMoneyOnlyConfiguredWorlds;
    //public static Boolean loseMoneyKillerWinMoney;


    public static Boolean updateChecker;

    public static String selectedLang;

    public static List<String> worldList;

    public static Boolean vaultLoseMoneyOnDeath;
    public static Boolean loseMoneyOnlyAtNight;
    public static Boolean loseMoneyOnlyInConfiguredWorlds;
    public static Boolean loseMoneyKillerReward;

    public static Boolean griefPreventionPvpInLand;

    public static Boolean placeholderPlaceholders;

    public static int autoPvpDayEnd;
    public static String autoPvpDayDifficulty;
    public static String autoPvpNightDifficulty;
    public static Boolean alertPlayersChat;
    public static Boolean alertPlayersTitle;

    public static Boolean playSoundPvpOff;
    public static Sound playSoundPvpOffSound;
    public static Float playSoundPvpOffVolume;
    public static Float playSoundPvpOffPitch;
    public static Boolean playSoundPvpOn;
    public static Sound playSoundPvpOnSound;
    public static Float playSoundPvpOnVolume;
    public static Float playSoundPvpOnPitch;





    public void updateConfigs() {

        //updateChecker = Boolean.parseBoolean(getValue("updateChecker"));
        //lang = getValue("lang");
        //worlds = getWorldList();
        //dayEnd = Integer.parseInt(getValue("pvp.dayEnd"));
        //dayDifficulty = getValue("pvp.dayDifficulty");
        //nightDifficulty = getValue("pvp.nightDifficulty");
        //dayEnabled = Boolean.parseBoolean(getValue("playSound.day.enabled"));
        //daySound = Sound.valueOf(getValue("playSound.day.sound"));
        //dayVolume = Float.valueOf(getValue("playSound.day.volume"));
        //dayPitch = Float.valueOf(getValue("playSound.day.pitch"));
        //nightEnabled = Boolean.parseBoolean(getValue("playSound.night.enabled"));
        //nightSound = Sound.valueOf(getValue("playSound.night.sound"));
        //nightVolume = Float.valueOf(getValue("playSound.night.volume"));
        //nightPitch = Float.valueOf(getValue("playSound.night.pitch"));
        //pvpAlert = Boolean.parseBoolean(getValue("pvpAlert"));
        //griefPreventionPvpProtection = Boolean.parseBoolean(getValue("compatibility.griefPreventionPvpProtection"));
        //enablePlaceholders = Boolean.parseBoolean(getValue("compatibility.placeholdersApi"));
        //vaultEnabled = Boolean.parseBoolean(getValue("compatibility.vault"));
        //loseMoneyEnabled = Boolean.parseBoolean(getValue("loseMoneyOnDeath.enabled"));
        //loseMoneyOnlyNight = Boolean.parseBoolean(getValue("loseMoneyOnDeath.onlyNight"));
        //loseMoneyOnlyConfiguredWorlds = Boolean.parseBoolean(getValue("loseMoneyOnDeath.onlyConfiguredWorlds"));
        //loseMoneyKillerWinMoney = Boolean.parseBoolean(getValue("loseMoneyOnDeath.killerWinMoney"));

        updateChecker = Boolean.parseBoolean(getValue("update-checker"));

        selectedLang = getValue("lang");

        worldList = getWorldList();

        vaultLoseMoneyOnDeath = Boolean.parseBoolean(getValue("vault.lose-money-on-death.enabled"));
        loseMoneyOnlyAtNight = Boolean.parseBoolean(getValue("vault.lose-money-on-death.only-at-night"));
        loseMoneyOnlyInConfiguredWorlds = Boolean.parseBoolean(getValue("vault.lose-money-on-death.only-in-configured-worlds"));
        loseMoneyKillerReward = Boolean.parseBoolean(getValue("vault.lose-money-on-death.killer-reward-money"));

        griefPreventionPvpInLand = Boolean.parseBoolean(getValue("grief-prevention.pvp-in-land"));

        placeholderPlaceholders = Boolean.parseBoolean(getValue("placeholder-api.placeholders"));

        autoPvpDayEnd = Integer.parseInt(getValue("automatic-pvp.day-end"));
        autoPvpDayDifficulty = getValue("automatic-pvp.night-difficulty");
        autoPvpNightDifficulty = getValue("automatic-pvp.day-difficulty");
        alertPlayersChat = Boolean.parseBoolean(getValue("automatic-pvp.alert-players.chat"));
        alertPlayersTitle = Boolean.parseBoolean(getValue("automatic-pvp.alert-players.title"));

        playSoundPvpOff = Boolean.parseBoolean(getValue("play-sound.pvp-off.enabled"));
        playSoundPvpOffSound = Sound.valueOf(getValue("play-sound.pvp-off.sound"));
        playSoundPvpOffVolume = Float.valueOf(getValue("play-sound.pvp-off.volume"));
        playSoundPvpOffPitch = Float.valueOf(getValue("play-sound.pvp-off.pitch"));
        playSoundPvpOn = Boolean.parseBoolean(getValue("play-sound.pvp-on.enabled"));
        playSoundPvpOnSound = Sound.valueOf(getValue("play-sound.pvp-on.sound"));
        playSoundPvpOnVolume = Float.valueOf(getValue("play-sound.pvp-on.volume"));
        playSoundPvpOnPitch = Float.valueOf(getValue("play-sound.pvp-on.pitch"));



    }

    private String getValue(String path) {
        String value = configFileConfig.getString(path);
        return value;
    }

    private List<String> getWorldList() {
        List<String> worldList = configFileConfig.getStringList("worlds");
        return worldList;
    }

    public void saveConfig() {
        try {
            configFileConfig.save(configFile);
        } catch (Exception e) {

        }
    }

}
