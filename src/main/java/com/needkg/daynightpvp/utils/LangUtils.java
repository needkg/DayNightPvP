package com.needkg.daynightpvp.utils;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.StartupFiles;
import org.bukkit.plugin.java.JavaPlugin;

public class LangUtils {

    public static String getString(String path) {
        String resultado = LangManager.currentLangFile.getString(path);
        assert resultado != null;
        return resultado.replaceAll("&", "§");
    }

    public static void selectLangFile(JavaPlugin plugin) {
        String pathLangFile = "lang/" + ConfigManager.selectedLang + ".yml";
        LangManager.currentLangFile = StartupFiles.loadConfigFile(plugin, pathLangFile);
    }

}
