package com.needkg.daynightpvp.command;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {

    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    public void register(JavaPlugin plugin) {

        var command = plugin.getCommand(name);

        if (command != null) {
            command.setExecutor(this);
            return;
        }

        throw new IllegalStateException("Command " + name + " not found in plugin.yml");

    }

}
