package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.ConsoleUtils;
import org.bukkit.Bukkit;

public class RegisterPlaceHolder {

    private final ConsoleUtils consoleUtils;
    private final PvpStatus pvpStatus;

    public RegisterPlaceHolder() {
        this.consoleUtils = new ConsoleUtils();
        this.pvpStatus = new PvpStatus();
    }

    public void register() {
        pvpStatus.unregister();
        registerPvpStatus();
    }
    public void registerPvpStatus() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && ConfigManager.enablePlaceholders) {
            consoleUtils.sendMessage("[DayNightPvP] PlaceholderAPI detected, starting compatibility...");
            pvpStatus.register();
        }
    }

}
