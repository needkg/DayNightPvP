package com.needkg.daynightpvp.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class RegisterCommands {
    public void register(JavaPlugin plugin) {

        plugin.getCommand("daynightpvp").setExecutor(new DnpCommand());
        plugin.getCommand("daynightpvp").setTabCompleter(new DnpTabCompleter());
    }
}
