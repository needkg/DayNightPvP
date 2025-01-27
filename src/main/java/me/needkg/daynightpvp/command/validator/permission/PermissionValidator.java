package me.needkg.daynightpvp.command.validator.permission;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
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
        return messageManager.getMessage(Message.SYSTEM_PERMISSION_DENIED);
    }
} 