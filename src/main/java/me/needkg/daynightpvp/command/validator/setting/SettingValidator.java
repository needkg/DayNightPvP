package me.needkg.daynightpvp.command.validator.setting;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class SettingValidator implements CommandValidator {
    private final Map<String, ?> settingsMap;
    private final MessageManager messageManager;

    public SettingValidator(Map<String, ?> settingsMap, MessageManager messageManager) {
        this.settingsMap = settingsMap;
        this.messageManager = messageManager;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 3) return true; // Deixa o ArgsLengthValidator lidar com isso
        return settingsMap.containsKey(args[2]);
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return messageManager.getMessage(Message.WORLD_EDITOR_FEEDBACK_INVALID_SETTING).replace("{0}", args[2]);
    }
} 