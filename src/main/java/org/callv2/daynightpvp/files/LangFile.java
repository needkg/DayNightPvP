package org.callv2.daynightpvp.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.utils.LoggingUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangFile {

    private static File fileLocation;
    private static FileConfiguration fileContent;
    private final ConfigFile configFile;

    public LangFile(ConfigFile configFile) {
        this.configFile = configFile;
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
        int latestFileVersion = 17;
        if (latestFileVersion != getVersion()) {
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "lang/" + configFile.getLanguage() + ".yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The 'lang/" + configFile.getLanguage() + ".yml' file was outdated and has been renamed to 'lang/" + configFile.getLanguage() + ".yml.old'.";
                LoggingUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the 'lang/" + configFile.getLanguage() + ".yml' file.";
                LoggingUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            fileContent = YamlConfiguration.loadConfiguration(fileLocation);
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
            // Regex para encontrar códigos hexadecimais no formato <#RRGGBB>
            Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]{6})>");
            Matcher matcher = hexPattern.matcher(text);
            StringBuffer buffer = new StringBuffer();

            // Substitui cada código hexadecimal encontrado pelo formato §x§R§R§G§G§B§B
            while (matcher.find()) {
                String hexCode = matcher.group(1);
                StringBuilder hexColor = new StringBuilder("§x");
                for (char c : hexCode.toCharArray()) {
                    hexColor.append('§').append(c);
                }
                matcher.appendReplacement(buffer, hexColor.toString());
            }
            matcher.appendTail(buffer);

            // Substitui os códigos '&' por '§'
            text = ChatColor.translateAlternateColorCodes('&', buffer.toString());
            return text;
        }
        return "Invalid message syntax";
    }

    // Version
    public int getVersion() {
        return fileContent.getInt("version");
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

    public String getNotifyPlayerImmune() {
        return formatMessage("notify-player-immune");
    }

    public String getNotifySelfImmune() {
        return formatMessage("notify-self-immune");
    }

    // Feedback
    public String getFeedbackUpdateAvailable() {
        return formatMessage("feedback-update-available");
    }

    public String getFeedbackUpdateCheckFailed() {
        return formatMessage("feedback-update-check-failed");
    }

    public String getFeedbackUpdateCurrentVersion() {
        return formatMessage("feedback-update-current-version");
    }

    public String getFeedbackUpdateLatestVersion() {
        return formatMessage("feedback-update-latest-version");
    }

    public String getFeedbackReloadPlugin() {
        return formatMessage("feedback-reload-plugin");
    }

    public String getFeedbackLoseMoney() {
        return formatMessage("feedback-lose-money");
    }

    public String getFeedbackWinMoney() {
        return formatMessage("feedback-win-money");
    }

    public String getFeedbackAddedWorld() {
        return formatMessage("feedback-added-world");
    }

    public String getFeedbackDeletedWorld() {
        return formatMessage("feedback-deleted-world");
    }

    public String getFeedbackWorldIsNotInSettings() {
        return formatMessage("feedback-world-is-not-in-settings");
    }

    public String getFeedbackWorldAlreadyExists() {
        return formatMessage("feedback-world-already-exists");
    }

    public String getFeedbackWorldDoesNotExist() {
        return formatMessage("feedback-world-does-not-exist");
    }

    public String getFeedbackIncorrectCommand() {
        return formatMessage("feedback-incorrect-command");
    }

    public String getFeedbackNonExistentCommand() {
        return formatMessage("feedback-non-existent-command");
    }

    public String getFeedbackBossbarSunset() {
        return formatMessage("feedback-boss-bar-sunset");
    }

    public String getFeedbackBossbarSunrise() {
        return formatMessage("feedback-boss-bar-sunrise");
    }

    public String getFeedbackError() {
        return formatMessage("feedback-error");
    }

    public String getFeedbackLangChanged() {
        return formatMessage("feedback-lang-changed");
    }

    // EditWorld Command Messages
    public String getFeedbackEditWorldCurrentValue() {
        return formatMessage("feedback-editworld-current-value");
    }

    public String getFeedbackEditWorldInvalidSetting() {
        return formatMessage("feedback-editworld-invalid-setting");
    }

    public String getFeedbackEditWorldInvalidValue() {
        return formatMessage("feedback-editworld-invalid-value");
    }

    public String getFeedbackEditWorldSuccess() {
        return formatMessage("feedback-editworld-success");
    }

    public String getFeedbackEditWorldUsage() {
        return formatMessage("feedback-editworld-usage");
    }

    public String getFeedbackEditWorldAvailableSettings() {
        return formatMessage("feedback-editworld-available-settings");
    }

    // EditWorld Command UI
    public String getEditWorldTitle() {
        return formatMessage("feedback-editworld-title");
    }

    public String getEditWorldSettingDetailsTitle() {
        return formatMessage("feedback-editworld-setting-details-title");
    }

    public String getEditWorldSettingDescription() {
        return formatMessage("feedback-editworld-setting-description");
    }

    public String getEditWorldSettingCurrentValue() {
        return formatMessage("feedback-editworld-setting-current-value");
    }

    public String getEditWorldSettingType() {
        return formatMessage("feedback-editworld-setting-type");
    }

    public String getEditWorldSettingRange() {
        return formatMessage("feedback-editworld-setting-range");
    }

    public String getEditWorldSettingSuggestions() {
        return formatMessage("feedback-editworld-setting-suggestions");
    }

    public String getFeedbackEditWorldSameValue() {
        return formatMessage("feedback-editworld-same-value");
    }

    // Ações
    public String getActionUpdateFoundClick() {
        return formatMessage("action-update-found-click");
    }

    // Placeholder
    public String getPlaceholderPvpEnabled() {
        return formatMessage("placeholder-pvp-enabled");
    }

    public String getPlaceholderPvpDisabled() {
        return formatMessage("placeholder-pvp-disabled");
    }

    public String getFeedbackErrorNoPermission() {
        return formatMessage("feedback-error-no-permission");
    }

    public String getFeedbackErrorLanguageInUse() {
        return formatMessage("feedback-error-language-in-use");
    }

}
