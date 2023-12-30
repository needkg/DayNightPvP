package org.callvdois.daynightpvp.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.config.LangManager;

public class LangUtils {

    public static String getString(String path) {
        String resultado = LangManager.currentLangFile.getString(path);
        assert resultado != null;
        return resultado.replaceAll("&", "§");
    }

    public static void selectLangFile(JavaPlugin plugin) {
        String pathLangFile = "lang/" + ConfigManager.selectedLang + ".yml";
        LangManager.currentLangFile = FilesManager.loadConfigFile(plugin, pathLangFile);
    }

}
