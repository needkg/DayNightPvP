package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.events.RegisterEvents;
import com.needkg.daynightpvp.placeholder.RegisterPlaceHolder;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesManager {

    private final String configVersion;
    private final String langVersion;
    private final String fileOutdated;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    public static List<String> langFiles = new ArrayList<>();

    public FilesManager() {
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        fileOutdated = "[DayNightPvP] The {0} file was an outdated version. it has been replaced by the new version.";

        configVersion = "9";
        langVersion = "7";
        langFiles = Arrays.asList("lang/en-US.yml", "lang/pt-BR.yml", "lang/es-ES.yml");
    }

    public void verifyConfigVersion() {
        if (!configVersion.equals(ConfigManager.configFileConfig.getString("version"))) {
            DayNightPvP.plugin.saveResource("config.yml", true);
            ConsoleUtils.warning(fileOutdated.replace("{0}", "config.yml"));
        }
    }

    public void verfiyLangsVersion(JavaPlugin plugin) {
        for (String fileName : langFiles) {
            File langFile = new File(plugin.getDataFolder(), fileName);
            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);
            String currentVersion = langFileConfig.getString("version");

            if (!langVersion.equals(currentVersion)) {
                plugin.saveResource("lang/" + langFile.getName(), true);
                ConsoleUtils.warning(fileOutdated.replace("{0}", "lang/" + langFile.getName()));
            }
        }
    }

    public void reloadPlugin(JavaPlugin plugin) {
        createFiles(plugin);
        ConfigManager.updateConfigs();
        LangManager.updateLangs(plugin);
        registerEvents.register();
        registerPlaceHolder.register();
    }

    public static void createFiles(JavaPlugin plugin) {
        ConfigManager.configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!ConfigManager.configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        ConfigManager.configFileConfig = YamlConfiguration.loadConfiguration(ConfigManager.configFile);

        for (String fileName : langFiles) {
            if (!new File(plugin.getDataFolder(), fileName).exists()) {
                plugin.saveResource(fileName, false);
            }
        }
    }

    public static FileConfiguration loadConfigFile(JavaPlugin plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

}
