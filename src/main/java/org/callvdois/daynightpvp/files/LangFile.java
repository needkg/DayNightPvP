package org.callvdois.daynightpvp.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.utils.ConsoleUtils;

import java.io.File;

public class LangFile {

    private static File fileLocation;
    private static FileConfiguration fileContent;
    private final ConfigFile configFile;
    private final ConsoleUtils consoleUtils;

    public LangFile() {
        configFile = new ConfigFile();
        consoleUtils = new ConsoleUtils();
    }

    public void createFile() {
        String filePath = "lang/" + configFile.getLanguage() + ".yml";
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), filePath);

        if (!fileLocation.exists()) {
            DayNightPvP.getInstance().saveResource(filePath, false);
        }

        loadFileContent();
        verifyFileVersion();
    }

    private void verifyFileVersion() {
        int latestFileVersion = 13;
        if (latestFileVersion != getVersion()) {
            resetFile();
            loadFileContent();
            String fileOutdated = "[DayNightPvP] The " + configFile.getLanguage() + ".yml file was an outdated version. It has been replaced by the new version.";
            consoleUtils.sendWarningMessage(fileOutdated);
        }
    }

    private void resetFile() {
        DayNightPvP.getInstance().saveResource("lang/" + configFile.getLanguage() + ".yml", true);
    }

    private void loadFileContent() {
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
    }

    private String formatMessage(String path) {
        String text = fileContent.getString(path);
        if (text != null) {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
        return "Invalid message syntax";
    }

    // Version
    public int getVersion() {
        return fileContent.getInt("version");
    }

    // Estados
    public String getStateEnabled() {
        return formatMessage("state-enabled");
    }

    public String getStateDisabled() {
        return formatMessage("state-disabled");
    }

    // Notificações
    public String getNotifyDayChat() {
        return formatMessage("notify-day-chat");
    }

    public String getNotifyNightChat() {
        return formatMessage("notify-night-chat");
    }

    public String getNotifyDayTitle() {
        return formatMessage("notify-day-title");
    }

    public String getNotifyDaySubtitle() {
        return formatMessage("notify-day-subtitle");
    }

    public String getNotifyNightTitle() {
        return formatMessage("notify-night-title");
    }

    public String getNotifyNightSubtitle() {
        return formatMessage("notify-night-subtitle");
    }

    public String getNotifyPvpDisabled() {
        return formatMessage("notify-pvp-disabled");
    }

    // Feedback
    public String getFeedbackUpdateFound() {
        return formatMessage("feedback-update-found");
    }

    public String getFeedbackUpdateFoundCurrentVersion() {
        return formatMessage("feedback-update-found-current-version");
    }

    public String getFeedbackUpdateFoundNewVersion() {
        return formatMessage("feedback-update-found-new-version");
    }

    public String getFeedbackReloadPlugin() {
        return formatMessage("feedback-reload-plugin");
    }

    public String getFeedbackSelectLang() {
        return formatMessage("feedback-select-lang");
    }

    public String getFeedbackLoseMoney() {
        return formatMessage("feedback-lose-money");
    }

    public String getFeedbackWinMoney() {
        return formatMessage("feedback-win-money");
    }

    public String getFeedbackNonExistentCommand() {
        return formatMessage("feedback-non-existent-command");
    }

    public String getFeedbackError() {
        return formatMessage("feedback-error");
    }

    // Interface
    public String getGuiLanguagesButton() {
        return formatMessage("gui-languages-button");
    }

    public String getGuiLanguagesButtonDescription() {
        return formatMessage("gui-languages-button-description");
    }

    public String getGuiLanguageButtonDescription() {
        return formatMessage("gui-language-button-description");
    }

    public String getGuiReloadButton() {
        return formatMessage("gui-reload-button");
    }

    public String getGuiReloadButtonDescription() {
        return formatMessage("gui-reload-button-description");
    }

    public String getGuiWorldsButton() {
        return formatMessage("gui-worlds-button");
    }

    public String getGuiWorldsButtonDescription() {
        return formatMessage("gui-worlds-button-description");
    }

    public String getGuiDayButton() {
        return formatMessage("gui-day-button");
    }

    public String getGuiDayButtonDescription() {
        return formatMessage("gui-day-button-description");
    }

    public String getGuiNightButton() {
        return formatMessage("gui-night-button");
    }

    public String getGuiNightButtonDescription() {
        return formatMessage("gui-night-button-description");
    }

    public String getGuiWorldButtonDescriptionDayNightPvp() {
        return formatMessage("gui-world-button-description-daynightpvp");
    }

    public String getGuiWorldButtonDescriptionType() {
        return formatMessage("gui-world-button-description-type");
    }

    public String getGuiWorldButtonDescriptionTime() {
        return formatMessage("gui-world-button-description-time");
    }

    public String getGuiWorldButtonDescriptionDay() {
        return formatMessage("gui-world-button-description-day");
    }

    public String getGuiWorldButtonDescriptionNight() {
        return formatMessage("gui-world-button-description-night");
    }

    public String getGuiWorldButtonDescriptionNotSupported() {
        return formatMessage("gui-world-button-description-not-supported");
    }

    public String getGuiDayNightPvpButton() {
        return formatMessage("gui-daynightpvp-button");
    }

    public String getGuiBackButton() {
        return formatMessage("gui-back-button");
    }

    public String getGuiBackButtonDescription() {
        return formatMessage("gui-back-button-description");
    }

    public String getGuiExitButton() {
        return formatMessage("gui-exit-button");
    }

    public String getGuiExitButtonDescription() {
        return formatMessage("gui-exit-button-description");
    }

    // Ações
    public String getActionUpdateFoundClick() {
        return formatMessage("action-update-found-click");
    }

    public String getActionButtonClickToSeeSettings() {
        return formatMessage("action-button-click-to-see-settings");
    }

    public String getActionButtonClickToDisable() {
        return formatMessage("action-button-click-to-disable");
    }

    public String getActionButtonClickToEnable() {
        return formatMessage("action-button-click-to-enable");
    }

    // Placeholder
    public String getPlaceholderPvpEnabled() {
        return formatMessage("placeholder-pvp-enabled");
    }

    public String getPlaceholderPvpDisabled() {
        return formatMessage("placeholder-pvp-disabled");
    }

}
