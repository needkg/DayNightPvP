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

    public static List<String> langFiles = new ArrayList<>();
    private final int configVersion;
    private final int langVersion;
    private final String fileOutdated;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public FilesManager() {
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        configManager = new ConfigManager();
        langManager = new LangManager();
        fileOutdated = "[DayNightPvP] The {0} file was an outdated version. it has been replaced by the new version.";

        configVersion = 15;
        langVersion = 12;
        langFiles = Arrays.asList("lang/en-US.yml", "lang/pt-BR.yml", "lang/es-ES.yml", "lang/ru-RU.yml");
    }

    public static FileConfiguration loadConfigFile(JavaPlugin plugin, String path) {
        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void verifyConfigVersion() {
        if (!(configVersion == configManager.getVersion())) {
            resetFile("config.yml");
            ConsoleUtils.warning(fileOutdated.replace("{0}", "config.yml"));
            ConfigManager.fileConfiguration = YamlConfiguration.loadConfiguration(ConfigManager.file);
        }
    }

    public void verfiyLangsVersion() {
        for (String fileName : langFiles) {
            File langFile = new File(DayNightPvP.getInstance().getDataFolder(), fileName);

            if (!(langVersion == langManager.getVersion())) {
                resetFile("lang/" + langFile.getName());
                ConsoleUtils.warning(fileOutdated.replace("{0}", "lang/" + langFile.getName()));
            }
        }
    }

    private void resetFile(String path) {
        DayNightPvP.getInstance().saveResource(path, true);
    }

    public void reloadPlugin() {
        createFiles();
        registerEvents.register();
        registerPlaceHolder.register();
    }

    public void createFiles() {
        ConfigManager.file = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml");
        if (!ConfigManager.file.exists()) {
            DayNightPvP.getInstance().saveResource("config.yml", false);
        }
        ConfigManager.fileConfiguration = YamlConfiguration.loadConfiguration(ConfigManager.file);
        verifyConfigVersion();

        for (String fileName : langFiles) {
            if (!new File(DayNightPvP.getInstance().getDataFolder(), fileName).exists()) {
                DayNightPvP.getInstance().saveResource(fileName, false);
            }
        }
        langManager.getLanguageFileSelected();
        verfiyLangsVersion();
    }

}
