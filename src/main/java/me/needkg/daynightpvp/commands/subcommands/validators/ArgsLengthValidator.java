package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.base.CommandValidator;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import org.bukkit.command.CommandSender;

public class ArgsLengthValidator implements CommandValidator {
    private final int expectedLength;
    private final String usage;
    private final SystemMessages systemMessages;

    public ArgsLengthValidator(int expectedLength, String usage, SystemMessages systemMessages) {
        this.expectedLength = expectedLength;
        this.usage = usage;
        this.systemMessages = systemMessages;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return args.length == expectedLength;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return systemMessages.getIncorrectCommandMessage().replace("{0}", usage);
    }
} 