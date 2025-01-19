package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LanguageValidator implements CommandValidator {
    private final List<String> availableLanguages;
    private final MessageManager messageManager;

    public LanguageValidator(List<String> availableLanguages, MessageManager messageManager) {
        this.availableLanguages = availableLanguages;
        this.messageManager = messageManager;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return args.length == 2 && availableLanguages.contains(args[1]);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return messageManager.getMessage(MessageType.SYSTEM_COMMAND_INCORRECT)
                .replace("{0}", "/dnp lang <" + String.join("/", availableLanguages) + ">");
    }
} 