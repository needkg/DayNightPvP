package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class WorldExistsValidator implements CommandValidator {
    private final MessageManager messageManager;
    private final boolean shouldExist;

    public WorldExistsValidator(boolean shouldExist, MessageManager messageManager) {
        this.messageManager = messageManager;
        this.shouldExist = shouldExist;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        boolean exists = Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]));
        return shouldExist ? exists : !exists;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return messageManager.getMessage(MessageType.SYSTEM_COMMAND_INCORRECT)
                    .replace("{0}", "/dnp editworld <world> <setting> <value>");
        } else {
            return messageManager.getMessage(MessageType.WORLD_EDITOR_ERROR_NOT_EXISTS)
                    .replace("{0}", args[1]);
        }

    }
} 