package me.needkg.daynightpvp.config;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.utils.LoggingUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class AbstractConfigurationFile {
    protected File fileLocation;
    protected FileConfiguration fileContent;
    
    protected abstract String getFilePath();
    protected abstract int getLatestFileVersion();
    
    public void initializeFile() {
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), getFilePath());
        if (!fileLocation.exists()) {
            LoggingUtils.sendWarningMessage("[DayNightPvP] File " + getFilePath() + " not found, creating a new one...");
            DayNightPvP.getInstance().saveResource(getFilePath(), false);
        }
        loadFileContent();
        validateFileVersion();
    }
    
    protected void validateFileVersion() {
        if (getLatestFileVersion() != getCurrentVersion()) {
            String backupPath = getFilePath() + ".old";
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), backupPath);
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                LoggingUtils.sendWarningMessage("[DayNightPvP] Outdated configuration file detected - '" + getFilePath() + "' has been backed up as '" + backupPath + "'");
            } else {
                LoggingUtils.sendWarningMessage("[DayNightPvP] Failed to rename the '" + getFilePath() + "' file.");
            }

            restoreDefaultFile();
            loadFileContent();
        }
    }
    
    protected void loadFileContent() {
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
    }
    
    public void restoreDefaultFile() {
        DayNightPvP.getInstance().saveResource(getFilePath(), true);
    }
    
    public void saveFile() {
        try {
            fileContent.save(fileLocation);
        } catch (Exception e) {
            LoggingUtils.sendWarningMessage("[DayNightPvP] Error saving file " + getFilePath() + ", resetting...");
            restoreDefaultFile();
        }
    }
    
    public int getCurrentVersion() {
        return fileContent.getInt("version");
    }
    
    public FileConfiguration getFileContent() {
        return fileContent;
    }
    
    public boolean hasPath(String path) {
        return fileContent.contains(path);
    }
    
    public void setValue(String path, Object value) {
        fileContent.set(path, value);
        saveFile();
    }
} 