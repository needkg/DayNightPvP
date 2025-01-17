package me.needkg.daynightpvp.command.subcommand.validators;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import org.bukkit.command.CommandSender;

public class PermissionValidator implements CommandValidator {
    private final String permission;
    private final SystemMessages systemMessages;

    public PermissionValidator(String permission, SystemMessages systemMessages) {
        this.permission = permission;
        this.systemMessages = systemMessages;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return systemMessages.getPermissionDeniedMessage();
    }
} 