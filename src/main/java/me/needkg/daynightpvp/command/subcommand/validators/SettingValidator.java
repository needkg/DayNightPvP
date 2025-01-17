package me.needkg.daynightpvp.command.subcommand.validators;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.configuration.message.WorldEditorMessages;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class SettingValidator implements CommandValidator {
    private final Map<String, ?> settingsMap;
    private final WorldEditorMessages worldEditorMessages;

    public SettingValidator(Map<String, ?> settingsMap, WorldEditorMessages worldEditorMessages) {
        this.settingsMap = settingsMap;
        this.worldEditorMessages = worldEditorMessages;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 3) return true; // Deixa o ArgsLengthValidator lidar com isso
        return settingsMap.containsKey(args[2]);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return worldEditorMessages.getInvalidSettingMessage().replace("{0}", args[2]);
    }
} 