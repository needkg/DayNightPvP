package org.callvdois.daynightpvp.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.callvdois.daynightpvp.DayNightPvP;

public class LangManager {

    public static FileConfiguration currentLangFile;
    private final ConfigManager configManager;

    public LangManager() {
        configManager = new ConfigManager();
    }

    public String getString(String path) {
        String text = currentLangFile.getString(path);
        if (text != null) {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
        return "Invalid message syntax";
    }

    public int getVersion() {
        return currentLangFile.getInt("version");
    }

    public void getLanguageFileSelected() {
        String pathLangFile = "lang/" + configManager.getString("language") + ".yml";
        currentLangFile = FilesManager.loadConfigFile(DayNightPvP.getInstance(), pathLangFile);
    }

}
