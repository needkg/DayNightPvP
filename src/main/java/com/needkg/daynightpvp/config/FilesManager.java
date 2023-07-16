package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FilesManager {

    private final LangManager langManager;
    private final ConfigManager configManager;
    private final ConsoleUtils consoleUtils;
    private final StartupFiles startupFiles;
    private final String configVersion;
    private final String langVersion;

    public FilesManager() {
        langManager = new LangManager();
        configManager = new ConfigManager();
        consoleUtils = new ConsoleUtils();
        startupFiles = new StartupFiles();
        configVersion = "7";
        langVersion = "6";
    }

    public void verifyConfigVersion(JavaPlugin plugin) {
        String currentVersion = StartupFiles.configFileConfig.getString("fileVersion");
        if (!configVersion.equals(currentVersion)) {
            String configPath = "config.yml";
            DayNightPvP.plugin.saveResource(configPath, true);
            consoleUtils.sendWarning(consoleUtils.FILE_OUTDATED.replace("{0}", configPath));
            reloadAllFiles(plugin);
        }
    }

    public void verfiyLangsVersion(JavaPlugin plugin) {
        for (String fileName : StartupFiles.langFiles) {
            File langFile = new File(plugin.getDataFolder(), fileName);
            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);
            String currentVersion = langFileConfig.getString("fileVersion");

            if (!langVersion.equals(currentVersion)) {
                plugin.saveResource("lang/" + langFile.getName(), true);
                consoleUtils.sendWarning(consoleUtils.FILE_OUTDATED.replace("{0}", "lang/" + langFile.getName()));
                reloadAllFiles(plugin);
            }
        }
    }

    public void reloadAllFiles(JavaPlugin plugin) {
        startupFiles.startConfigFile(plugin);
        startupFiles.startLangsFile(plugin);
        configManager.updateConfigs();
        startupFiles.selectLangFile(plugin);
        langManager.updateLangs(plugin);
    }

}
