package me.needkg.daynightpvp.command.validator.args;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import org.bukkit.command.CommandSender;

public class ArgsLengthValidator implements CommandValidator {
    private final int expectedLength;
    private final String usage;
    private final MessageManager messageManager;

    public ArgsLengthValidator(int expectedLength, String usage, MessageManager messageManager) {
        this.expectedLength = expectedLength;
        this.usage = usage;
        this.messageManager = messageManager;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        return args.length == expectedLength;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return messageManager.getMessage(Message.SYSTEM_COMMAND_INCORRECT).replace("{0}", usage);
    }
} 