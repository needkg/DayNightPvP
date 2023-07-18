package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public class LangManager {

    public static FileConfiguration currentLangFile;

    public static String onMessage;
    public static String offMessage;
    public static String dayMessage;
    public static String nightMessage;
    public static String reloadedConfig;
    public static String updateFoundClick;
    public static String langSelected;
    public static String loseMoneyMessage;
    public static String winMoneyMessage;
    public static String updateFoundMessage;
    public static String langButton;
    public static String portugueseButton;
    public static String englishButton;
    public static String spanishButton;
    public static String langButtonDescription1;
    public static String langButtonDescription2;
    public static String reloadButton;
    public static String reloadButtonDescription;
    public static String worldsButton;
    public static String worldsButtonDescription;
    public static String dayButton;
    public static String dayButtonDescription;
    public static String nightButton;
    public static String worldButtonDescriptionNotSupported;
    public static String nightButtonDescription;
    public static String worldButtonDescriptionLine1;
    public static String worldButtonDescriptionLine2;
    public static String worldButtonDescriptionWorldType;
    public static String worldButtonDescriptionLine3;
    public static String settingsButton1;
    public static String settingsButton2;
    public static String settingsButtonDescription1;
    public static String settingsButtonDescription2;
    public static String backButton;
    public static String backButtonDescription1;
    public static String backButtonDescription2;
    public static String exitButton;
    public static String exitButtonDescription;

    public void updateLangs(JavaPlugin plugin) {

        selectLangFile(plugin);

        onMessage = getString("onMessage");
        offMessage = getString("offMessage");
        dayMessage = getString("dayMessage");
        nightMessage = getString("nightMessage");
        reloadedConfig = getString("reloadedConfig");
        updateFoundMessage = getString("updateFoundMessage");
        updateFoundClick = getString("updateFoundClick");
        langSelected = getString("langSelected");
        loseMoneyMessage = getString("loseMoneyMessage");
        winMoneyMessage = getString("winMoneyMessage");

        langButton = getString("langButton");
        portugueseButton = getString("portugueseButton");
        englishButton = getString("englishButton");
        spanishButton = getString("spanishButton");
        langButtonDescription1 = getString("langButtonDescription1");
        langButtonDescription2 = getString("langButtonDescription2");
        reloadButton = getString("reloadButton");
        reloadButtonDescription = getString("reloadButtonDescription");
        worldsButton = getString("worldsButton");
        worldsButtonDescription = getString("worldsButtonDescription");
        dayButton = getString("dayButton");
        dayButtonDescription = getString("dayButtonDescription");
        nightButton = getString("nightButton");
        worldButtonDescriptionNotSupported = getString("worldButtonDescriptionNotSupported");
        nightButtonDescription = getString("nightButtonDescription");
        worldButtonDescriptionLine1 = getString("worldButtonDescriptionLine1");
        worldButtonDescriptionLine2 = getString("worldButtonDescriptionLine2");
        worldButtonDescriptionWorldType = getString("worldButtonDescriptionWorldType");
        worldButtonDescriptionLine3 = getString("worldButtonDescriptionLine3");
        settingsButton1 = getString("settingsButton1");
        settingsButton2 = getString("settingsButton2");
        settingsButtonDescription1 = getString("settingsButtonDescription1");
        settingsButtonDescription2 = getString("settingsButtonDescription2");
        backButton = getString("backButton");
        backButtonDescription1 = getString("backButtonDescription1");
        backButtonDescription2 = getString("backButtonDescription2");
        exitButton = getString("exitButton");
        exitButtonDescription = getString("exitButtonDescription");
    }

    public static String getString(String path) {
        String resultado = currentLangFile.getString(path);
        if (resultado != null) {
            resultado = resultado.replaceAll("&", "§");
        }
        return resultado;
    }

    public void selectLangFile(JavaPlugin plugin) {
        String pathLangFile = "lang/" + ConfigManager.lang + ".yml";
        LangManager.currentLangFile = StartupFiles.loadConfigFile(plugin, pathLangFile);
    }

}
