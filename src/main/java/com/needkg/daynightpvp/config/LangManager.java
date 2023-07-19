package com.needkg.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LangManager {

    public static FileConfiguration currentLangFile;

    public static String onMessage;
    public static String offMessage;
    public static String dayChatMessage;
    public static String nightChatMessage;
    public static String dayTitleMessage;
    public static String daySubTitleMessage;
    public static String nightTitleMessage;
    public static String nightSubTitleMessage;
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

        onMessage = getString("on-message");
        offMessage = getString("off-message");
        dayChatMessage = getString("day-chat-message");
        nightChatMessage = getString("night-chat-message");
        dayTitleMessage = getString("day-title-message");
        daySubTitleMessage = getString("day-sub-title-message");
        nightTitleMessage = getString("night-title-message");
        nightSubTitleMessage = getString("night-sub-title-message");
        reloadedConfig = getString("reloaded-config");
        updateFoundMessage = getString("update-found-message");
        updateFoundClick = getString("update-found-click");
        langSelected = getString("lang-selected");
        loseMoneyMessage = getString("lose-money-message");
        winMoneyMessage = getString("win-money-message");

        langButton = getString("lang-button");
        portugueseButton = getString("portuguese-button");
        englishButton = getString("english-button");
        spanishButton = getString("spanish-button");
        langButtonDescription1 = getString("lang-button-description1");
        langButtonDescription2 = getString("lang-button-description2");
        reloadButton = getString("reload-button");
        reloadButtonDescription = getString("reload-button-description");
        worldsButton = getString("worlds-button");
        worldsButtonDescription = getString("worlds-button-description");
        dayButton = getString("day-button");
        dayButtonDescription = getString("day-button-description");
        nightButton = getString("night-button");
        worldButtonDescriptionNotSupported = getString("world-button-description-not-supported");
        nightButtonDescription = getString("night-button-description");
        worldButtonDescriptionLine1 = getString("world-button-description-line1");
        worldButtonDescriptionLine2 = getString("world-button-description-line2");
        worldButtonDescriptionWorldType = getString("world-button-description-world-type");
        worldButtonDescriptionLine3 = getString("world-button-description-line3");
        settingsButton1 = getString("settings-button1");
        settingsButton2 = getString("settings-button2");
        settingsButtonDescription1 = getString("settings-button-description1");
        settingsButtonDescription2 = getString("settings-button-description2");
        backButton = getString("back-button");
        backButtonDescription1 = getString("back-button-description1");
        backButtonDescription2 = getString("back-button-description2");
        exitButton = getString("exit-button");
        exitButtonDescription = getString("exit-button-description");
    }

    public static String getString(String path) {
        String resultado = currentLangFile.getString(path);
        if (resultado != null) {
            resultado = resultado.replaceAll("&", "§");
        }
        return resultado;
    }

    public void selectLangFile(JavaPlugin plugin) {
        String pathLangFile = "lang/" + ConfigManager.selectedLang + ".yml";
        LangManager.currentLangFile = StartupFiles.loadConfigFile(plugin, pathLangFile);
    }

}
