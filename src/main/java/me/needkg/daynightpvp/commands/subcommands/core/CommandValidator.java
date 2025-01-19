package me.needkg.daynightpvp.commands.subcommands.core;

import org.bukkit.command.CommandSender;

public interface CommandValidator {
    boolean validate(CommandSender sender, String[] args);

    String getErrorMessage(CommandSender sender, String[] args);
} 