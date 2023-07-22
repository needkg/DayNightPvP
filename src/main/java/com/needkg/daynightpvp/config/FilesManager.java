package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.events.RegisterEvents;
import com.needkg.daynightpvp.placeholder.RegisterPlaceHolder;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import com.needkg.daynightpvp.utils.LangUtils;
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

        configVersion = "8";
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
        for (String fileName : FilesManager.langFiles) {
            File langFile = new File(plugin.getDataFolder(), fileName);
            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);
            String currentVersion = langFileConfig.getString("version");

            if (!langVersion.equals(currentVersion)) {
                plugin.saveResource("lang/" + langFile.getName(), true);
                ConsoleUtils.warning(fileOutdated.replace("{0}", "lang/" + langFile.getName()));
            }
        }
        reloadPlugin(plugin);
    }

    public void reloadPlugin(JavaPlugin plugin) {
        StartupFiles.startConfigFile(plugin);
        StartupFiles.startLangsFile(plugin);
        ConfigManager.updateConfigs();
        LangUtils.selectLangFile(plugin);
        LangManager.updateLangs(plugin);
        registerEvents.register();
        registerPlaceHolder.register();
    }

}
