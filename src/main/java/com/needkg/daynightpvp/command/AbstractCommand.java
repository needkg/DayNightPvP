package com.needkg.daynightpvp.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractCommand implements CommandExecutor {

    public void register(JavaPlugin plugin) {

        var command = plugin.getCommand(getName());

        if (command != null) {
            command.setExecutor(this);
        }

    }

    protected abstract String getName();

}
