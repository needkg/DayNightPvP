package me.needkg.daynightpvp.configuration.file;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class YamlFileBase {

    protected File fileLocation;
    protected FileConfiguration fileContent;

    protected abstract String getFilePath();

    protected abstract int getLatestFileVersion();

    public void initializeFile() {
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), getFilePath());
        if (!fileLocation.exists()) {
            Logger.warning("[DayNightPvP] Configuration file not found - '" + getFilePath() + "', creating...");
            DayNightPvP.getInstance().saveResource(getFilePath(), false);
        }
        loadFileContent();
        validateFileVersion();
    }

    protected void loadFileContent() {
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
    }

    protected void validateFileVersion() {
        if (getLatestFileVersion() != getCurrentVersion()) {
            File backupDir = new File(DayNightPvP.getInstance().getDataFolder(), "backup");
            File backupLangDir = new File(DayNightPvP.getInstance().getDataFolder(), "backup/lang");
            if (!backupDir.exists()) {
                backupDir.mkdir();
            }
            if (!backupLangDir.mkdirs()) {
                backupLangDir.mkdirs();
            }

            String backupPath = "backup" + File.separator + getFilePath() + "_v" + getCurrentVersion();
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), backupPath);
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                Logger.warning("[DayNightPvP] Outdated configuration file detected - '" + getFilePath() + "' has been backed up as '" + backupPath + "'");
            } else {
                Logger.warning("[DayNightPvP] Failed to rename the '" + getFilePath() + "' file.");
            }

            restoreDefaultFile();
            loadFileContent();
        }
    }

    public void refreshFile() {
        initializeFile();
    }

    public void restoreDefaultFile() {
        DayNightPvP.getInstance().saveResource(getFilePath(), true);
    }

    public void saveFile() {
        try {
            fileContent.save(fileLocation);
        } catch (Exception e) {
            Logger.warning("[DayNightPvP] Error saving file " + getFilePath() + ", resetting...");
            restoreDefaultFile();
        }
    }

    public void setValue(String path, Object value) {
        fileContent.set(path, value);
        saveFile();
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
} 