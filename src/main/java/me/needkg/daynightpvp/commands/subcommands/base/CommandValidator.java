package me.needkg.daynightpvp.commands.subcommands.base;

import org.bukkit.command.CommandSender;

public interface CommandValidator {
    boolean validate(CommandSender sender, String[] args);
    String getErrorMessage(CommandSender sender, String[] args);
} 