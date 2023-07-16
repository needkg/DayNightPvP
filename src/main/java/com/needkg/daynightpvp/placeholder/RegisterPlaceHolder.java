package com.needkg.daynightpvp.placeholder;

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
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            consoleUtils.sendMessage("PlaceholderAPI detected, starting compatibility...");
            pvpStatus.register();
        }
    }

}
