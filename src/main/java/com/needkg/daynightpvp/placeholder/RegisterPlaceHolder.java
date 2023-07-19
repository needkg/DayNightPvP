package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RegisterPlaceHolder {

    private PvpStatus pvpStatus;

    public void reload() {
        pvpStatus = new PvpStatus();
        pvpStatus.unregister();
        registerPvpStatus();
    }
    public void registerPvpStatus() {
        boolean placeholders = ConfigManager.placeholderPlaceholders;
        if (isPlaceholderAPIInstalled()) {
            if (placeholders) {
                pvpStatus = new PvpStatus();
                pvpStatus.register();
            }
        }
    }

    public boolean isPlaceholderAPIInstalled() {
        Plugin placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        return placeholderAPI != null && placeholderAPI.isEnabled();
    }

}
