package org.callvdois.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.events.RegisterEvents;
import org.callvdois.daynightpvp.placeholder.RegisterPlaceHolder;
import org.callvdois.daynightpvp.utils.ConsoleUtils;

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

        configVersion = "10";
        langVersion = "7";
        langFiles = Arrays.asList("lang/en-US.yml", "lang/pt-BR.yml", "lang/es-ES.yml", "lang/ru-RU.yml");
    }

    public void verifyConfigVersion() {
        if (!configVersion.equals(ConfigManager.configFileConfig.getString("version"))) {
            resetFile("config.yml");
            ConsoleUtils.warning(fileOutdated.replace("{0}", "config.yml"));
            ConfigManager.configFileConfig = YamlConfiguration.loadConfiguration(ConfigManager.configFile);
        }
    }

    public void verfiyLangsVersion() {
        for (String fileName : langFiles) {
            File langFile = new File(DayNightPvP.getInstance().getDataFolder(), fileName);
            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);
            String currentVersion = langFileConfig.getString("version");

            if (!langVersion.equals(currentVersion)) {
                resetFile("lang/" + langFile.getName());
                ConsoleUtils.warning(fileOutdated.replace("{0}", "lang/" + langFile.getName()));
            }
        }
    }

    public void resetFile(String path) {
        DayNightPvP.getInstance().saveResource(path, true);
    }

    public void reloadPlugin() {
        createFiles();
        ConfigManager.updateConfigs();
        LangManager.updateLangs();
        registerEvents.register();
        registerPlaceHolder.register();
    }

    public void createFiles() {
        ConfigManager.configFile = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml");
        if (!ConfigManager.configFile.exists()) {
            DayNightPvP.getInstance().saveResource("config.yml", false);
        }
        ConfigManager.configFileConfig = YamlConfiguration.loadConfiguration(ConfigManager.configFile);

        for (String fileName : langFiles) {
            if (!new File(DayNightPvP.getInstance().getDataFolder(), fileName).exists()) {
                DayNightPvP.getInstance().saveResource(fileName, false);
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
