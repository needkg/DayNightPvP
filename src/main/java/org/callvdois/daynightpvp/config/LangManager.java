package org.callvdois.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.utils.LangUtils;

public class LangManager {

    public static FileConfiguration currentLangFile;

    public static String version;
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
    public static String notifyPvpIsDisabled;
    public static String nonExistentCommand;
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

    public static void updateLangs() {
        LangUtils.selectLangFile(DayNightPvP.getInstance());

        onMessage = LangUtils.getString("on-message");
        offMessage = LangUtils.getString("off-message");
        dayChatMessage = LangUtils.getString("day-chat-message");
        nightChatMessage = LangUtils.getString("night-chat-message");
        dayTitleMessage = LangUtils.getString("day-title-message");
        daySubTitleMessage = LangUtils.getString("day-sub-title-message");
        nightTitleMessage = LangUtils.getString("night-title-message");
        nightSubTitleMessage = LangUtils.getString("night-sub-title-message");
        reloadedConfig = LangUtils.getString("reloaded-config");
        updateFoundMessage = LangUtils.getString("update-found-message");
        updateFoundClick = LangUtils.getString("update-found-click");
        langSelected = LangUtils.getString("lang-selected");
        loseMoneyMessage = LangUtils.getString("lose-money-message");
        winMoneyMessage = LangUtils.getString("win-money-message");
        notifyPvpIsDisabled = LangUtils.getString("notifyPvpIsDisabled");
        nonExistentCommand = LangUtils.getString("nonExistentCommand");

        langButton = LangUtils.getString("lang-button");
        portugueseButton = LangUtils.getString("portuguese-button");
        englishButton = LangUtils.getString("english-button");
        spanishButton = LangUtils.getString("spanish-button");
        langButtonDescription1 = LangUtils.getString("lang-button-description1");
        langButtonDescription2 = LangUtils.getString("lang-button-description2");
        reloadButton = LangUtils.getString("reload-button");
        reloadButtonDescription = LangUtils.getString("reload-button-description");
        worldsButton = LangUtils.getString("worlds-button");
        worldsButtonDescription = LangUtils.getString("worlds-button-description");
        dayButton = LangUtils.getString("day-button");
        dayButtonDescription = LangUtils.getString("day-button-description");
        nightButton = LangUtils.getString("night-button");
        worldButtonDescriptionNotSupported = LangUtils.getString("world-button-description-not-supported");
        nightButtonDescription = LangUtils.getString("night-button-description");
        worldButtonDescriptionLine1 = LangUtils.getString("world-button-description-line1");
        worldButtonDescriptionLine2 = LangUtils.getString("world-button-description-line2");
        worldButtonDescriptionWorldType = LangUtils.getString("world-button-description-world-type");
        worldButtonDescriptionLine3 = LangUtils.getString("world-button-description-line3");
        settingsButton1 = LangUtils.getString("settings-button1");
        settingsButton2 = LangUtils.getString("settings-button2");
        settingsButtonDescription1 = LangUtils.getString("settings-button-description1");
        settingsButtonDescription2 = LangUtils.getString("settings-button-description2");
        backButton = LangUtils.getString("back-button");
        backButtonDescription1 = LangUtils.getString("back-button-description1");
        backButtonDescription2 = LangUtils.getString("back-button-description2");
        exitButton = LangUtils.getString("exit-button");
        exitButtonDescription = LangUtils.getString("exit-button-description");
    }

}
