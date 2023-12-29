package org.callvdois.daynightpvp.config;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.callvdois.daynightpvp.utils.ConfigUtils;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration configFileConfig;

    public static boolean updateChecker;

    public static String selectedLang;

    public static List<String> worldList;

    public static boolean vaultLoseMoneyOnDeath;
    public static boolean loseMoneyOnlyAtNight;
    public static boolean loseMoneyOnlyInConfiguredWorlds;
    public static boolean loseMoneyKillerReward;

    public static boolean griefPreventionPvpInLand;

    public static boolean placeholderPlaceholders;


    public static boolean keepInventoryWhenKilledByPlayer;

    public static int autoPvpDayEnd;
    public static boolean automaticDifficulty;
    public static String automaticDifficultyDay;
    public static String automaticDifficultyNight;
    public static boolean alertPlayersChat;
    public static boolean alertPlayersTitle;
    public static int alertPlayersFadeIn;
    public static int alertPlayersStay;
    public static int alertPlayersFadeOut;

    public static boolean playSoundPvpOff;
    public static Sound playSoundPvpOffSound;
    public static float playSoundPvpOffVolume;
    public static float playSoundPvpOffPitch;
    public static boolean playSoundPvpOn;
    public static Sound playSoundPvpOnSound;
    public static float playSoundPvpOnVolume;
    public static float playSoundPvpOnPitch;

    public static void updateConfigs() {

        updateChecker = Boolean.parseBoolean(ConfigUtils.getValue("update-checker"));

        selectedLang = ConfigUtils.getValue("lang");

        worldList = ConfigUtils.getList();

        vaultLoseMoneyOnDeath = ConfigUtils.getBoolean("vault.lose-money-on-death.enabled");
        loseMoneyOnlyAtNight = ConfigUtils.getBoolean("vault.lose-money-on-death.only-at-night");
        loseMoneyOnlyInConfiguredWorlds = ConfigUtils.getBoolean("vault.lose-money-on-death.only-in-configured-worlds");
        loseMoneyKillerReward = ConfigUtils.getBoolean("vault.lose-money-on-death.killer-reward-money");

        griefPreventionPvpInLand = ConfigUtils.getBoolean("grief-prevention.pvp-in-land");

        placeholderPlaceholders = ConfigUtils.getBoolean("placeholder-api.placeholders");

        keepInventoryWhenKilledByPlayer = ConfigUtils.getBoolean("pvp.keep-inventory-when-killed-by-player");

        autoPvpDayEnd = Integer.parseInt(ConfigUtils.getValue("automatic-pvp.day-end"));
        automaticDifficulty = ConfigUtils.getBoolean("automatic-pvp.automatic-difficulty.enabled");
        automaticDifficultyNight = ConfigUtils.getValue("automatic-pvp.automatic-difficulty.night");
        automaticDifficultyDay = ConfigUtils.getValue("automatic-pvp.automatic-difficulty.day");
        alertPlayersChat = ConfigUtils.getBoolean("automatic-pvp.alert-players.chat");
        alertPlayersTitle = ConfigUtils.getBoolean("automatic-pvp.alert-players.title.enabled");
        alertPlayersFadeIn = Integer.parseInt(ConfigUtils.getValue("automatic-pvp.alert-players.title.fade-in"));
        alertPlayersStay = Integer.parseInt(ConfigUtils.getValue("automatic-pvp.alert-players.title.stay"));
        alertPlayersFadeOut = Integer.parseInt(ConfigUtils.getValue("automatic-pvp.alert-players.title.fade-out"));

        playSoundPvpOff = ConfigUtils.getBoolean("play-sound.pvp-off.enabled");
        playSoundPvpOffSound = Sound.valueOf(ConfigUtils.getValue("play-sound.pvp-off.sound"));
        playSoundPvpOffVolume = Float.parseFloat(ConfigUtils.getValue("play-sound.pvp-off.volume"));
        playSoundPvpOffPitch = Float.parseFloat(ConfigUtils.getValue("play-sound.pvp-off.pitch"));
        playSoundPvpOn = ConfigUtils.getBoolean("play-sound.pvp-on.enabled");
        playSoundPvpOnSound = Sound.valueOf(ConfigUtils.getValue("play-sound.pvp-on.sound"));
        playSoundPvpOnVolume = Float.parseFloat(ConfigUtils.getValue("play-sound.pvp-on.volume"));
        playSoundPvpOnPitch = Float.parseFloat(ConfigUtils.getValue("play-sound.pvp-on.pitch"));
    }

}
