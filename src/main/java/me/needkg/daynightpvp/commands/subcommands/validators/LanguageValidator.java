package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.base.CommandValidator;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LanguageValidator implements CommandValidator {
    private final List<String> availableLanguages;
    private final SystemMessages systemMessages;

    public LanguageValidator(List<String> availableLanguages, SystemMessages systemMessages) {
        this.availableLanguages = availableLanguages;
        this.systemMessages = systemMessages;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return args.length == 2 && availableLanguages.contains(args[1]);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return systemMessages.getIncorrectCommandMessage()
                .replace("{0}", "/dnp lang <" + String.join("/", availableLanguages) + ">");
    }
} 