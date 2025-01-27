package me.needkg.daynightpvp.command.validator.language;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
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
        return messageManager.getMessage(Message.SYSTEM_COMMAND_INCORRECT)
                .replace("{0}", "/dnp lang <" + String.join("/", availableLanguages) + ">");
    }
} 