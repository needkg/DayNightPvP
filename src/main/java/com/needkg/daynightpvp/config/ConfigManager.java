package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.utils.ConfigUtils;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration configFileConfig;

    public static Boolean updateChecker;

    public static String selectedLang;

    public static List<String> worldList;

    public static Boolean vaultLoseMoneyOnDeath;
    public static Boolean loseMoneyOnlyAtNight;
    public static Boolean loseMoneyOnlyInConfiguredWorlds;
    public static Boolean loseMoneyKillerReward;

    public static Boolean griefPreventionPvpInLand;

    public static Boolean placeholderPlaceholders;


    public static boolean keepInventoryWhenKilledByPlayer;

    public static int autoPvpDayEnd;
    public static boolean automaticDifficulty;
    public static String automaticDifficultyDay;
    public static String automaticDifficultyNight;
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
        alertPlayersTitle = ConfigUtils.getBoolean("automatic-pvp.alert-players.title");

        playSoundPvpOff = ConfigUtils.getBoolean("play-sound.pvp-off.enabled");
        playSoundPvpOffSound = Sound.valueOf(ConfigUtils.getValue("play-sound.pvp-off.sound"));
        playSoundPvpOffVolume = Float.valueOf(ConfigUtils.getValue("play-sound.pvp-off.volume"));
        playSoundPvpOffPitch = Float.valueOf(ConfigUtils.getValue("play-sound.pvp-off.pitch"));
        playSoundPvpOn = ConfigUtils.getBoolean("play-sound.pvp-on.enabled");
        playSoundPvpOnSound = Sound.valueOf(ConfigUtils.getValue("play-sound.pvp-on.sound"));
        playSoundPvpOnVolume = Float.valueOf(ConfigUtils.getValue("play-sound.pvp-on.volume"));
        playSoundPvpOnPitch = Float.valueOf(ConfigUtils.getValue("play-sound.pvp-on.pitch"));
    }

}
