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
    private final StartupFiles startupFiles;
    private final String configVersion;
    private final String langVersion;

    public FilesManager() {
        langManager = new LangManager();
        configManager = new ConfigManager();
        startupFiles = new StartupFiles();
        configVersion = "7";
        langVersion = "7";
    }

    public void verifyConfigVersion(JavaPlugin plugin) {
        String currentVersion = ConfigManager.configFileConfig.getString("version");
        if (!configVersion.equals(currentVersion)) {
            String configPath = "config.yml";
            DayNightPvP.plugin.saveResource(configPath, true);
            ConsoleUtils.sendWarning(ConsoleUtils.FILE_OUTDATED.replace("{0}", configPath));
            reloadPlugin(plugin);
        }
    }

    public void verfiyLangsVersion(JavaPlugin plugin) {
        for (String fileName : StartupFiles.langFiles) {
            File langFile = new File(plugin.getDataFolder(), fileName);
            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);
            String currentVersion = langFileConfig.getString("version");

            if (!langVersion.equals(currentVersion)) {
                plugin.saveResource("lang/" + langFile.getName(), true);
                ConsoleUtils.sendWarning(ConsoleUtils.FILE_OUTDATED.replace("{0}", "lang/" + langFile.getName()));
                reloadPlugin(plugin);
            }
        }
    }

    public void reloadPlugin(JavaPlugin plugin) {
        startupFiles.startConfigFile(plugin);
        startupFiles.startLangsFile(plugin);
        configManager.updateConfigs();
        langManager.selectLangFile(plugin);
        langManager.updateLangs(plugin);
    }

}
