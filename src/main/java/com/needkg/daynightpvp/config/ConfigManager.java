package com.needkg.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.utils.LoggingUtils;

import java.io.File;

public class ConfigManager {
    private File fileLocation;
    private FileConfiguration fileContent;

    public void createFile() {
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml");
        if (!fileLocation.exists()) {
            DayNightPvP.getInstance().saveResource("config.yml", false);
        }
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        verifyFileVersion();
    }

    private void verifyFileVersion() {
        int latestFileVersion = 20;
        if (latestFileVersion != getVersion()) {
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The 'config.yml' file was outdated and has been renamed to 'config.yml.old'.";
                LoggingUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the 'config.yml' file.";
                LoggingUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        }
    }

    public boolean contains(String text) {
        return fileContent.contains(text);
    }

    public void setValue(String path, Object value) {
        fileContent.set(path, value);
        saveConfig();
    }

    public void saveConfig() {
        try {
            fileContent.save(fileLocation);
        } catch (Exception e) {
            LoggingUtils.sendWarningMessage("[DayNightPvP] Error saving configuration file, resetting...");
            resetFile();
        }
    }

    public void resetFile() {
        DayNightPvP.getInstance().saveResource("config.yml", true);
    }

    public int getVersion() {
        return fileContent.getInt("version");
    }

    public FileConfiguration getFileContent() {
        return fileContent;
    }
} 