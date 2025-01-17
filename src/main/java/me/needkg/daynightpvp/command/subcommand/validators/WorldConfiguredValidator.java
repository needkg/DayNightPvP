package me.needkg.daynightpvp.command.subcommand.validators;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.message.WorldEditorMessages;
import org.bukkit.command.CommandSender;

public class WorldConfiguredValidator implements CommandValidator {
    private final ConfigurationManager configurationManager;
    private final WorldEditorMessages worldEditorMessages;
    private final boolean shouldBeConfigured;

    public WorldConfiguredValidator(ConfigurationManager configurationManager, WorldEditorMessages worldEditorMessages, boolean shouldBeConfigured) {
        this.configurationManager = configurationManager;
        this.worldEditorMessages = worldEditorMessages;
        this.shouldBeConfigured = shouldBeConfigured;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        boolean isConfigured = configurationManager.hasPath("worlds." + args[1]);
        return shouldBeConfigured ? isConfigured : !isConfigured;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return shouldBeConfigured 
            ? worldEditorMessages.getWorldNotConfiguredMessage().replace("{0}", args[1])
            : worldEditorMessages.getWorldAlreadyExistsMessage().replace("{0}", args[1]);
    }
} 