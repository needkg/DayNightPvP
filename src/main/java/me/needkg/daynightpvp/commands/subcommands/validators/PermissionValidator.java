package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import org.bukkit.command.CommandSender;

public class PermissionValidator implements CommandValidator {
    private final String permission;
    private final MessageManager messageManager;

    public PermissionValidator(String permission, MessageManager messageManager) {
        this.permission = permission;
        this.messageManager = messageManager;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return messageManager.getMessage(MessageType.SYSTEM_PERMISSION_DENIED);
    }
} 