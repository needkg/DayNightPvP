package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.settings.GeneralSettings;
import com.needkg.daynightpvp.utils.LoggingUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {
    private final GeneralSettings generalSettings;
    private File fileLocation;
    private FileConfiguration fileContent;

    public LangManager(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
    }

    public void createFile() {
        String filePath = "lang/" + generalSettings.getLanguage() + ".yml";
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
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "lang/" + generalSettings.getLanguage() + ".yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The 'lang/" + generalSettings.getLanguage() + ".yml' file was outdated and has been renamed to 'lang/" + generalSettings.getLanguage() + ".yml.old'.";
                LoggingUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the 'lang/" + generalSettings.getLanguage() + ".yml' file.";
                LoggingUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            loadFileContent();
        }
    }

    private void resetFile() {
        DayNightPvP.getInstance().saveResource("lang/" + generalSettings.getLanguage() + ".yml", true);
    }

    private void loadFileContent() {
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
    }

    public int getVersion() {
        return fileContent.getInt("version");
    }

    public FileConfiguration getFileContent() {
        return fileContent;
    }
} 