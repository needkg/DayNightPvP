package me.needkg.daynightpvp.command.subcommand.base;

import org.bukkit.command.CommandSender;

public interface CommandValidator {
    boolean validate(CommandSender sender, String[] args);
    String getErrorMessage(CommandSender sender, String[] args);
} 