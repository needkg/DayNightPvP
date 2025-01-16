package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.ISubCommand;
import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.settings.GeneralSettings;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.services.PluginServices;
import me.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LangSubCommand implements ISubCommand {

    private static final List<String> AVAILABLE_LANGUAGES = Arrays.asList("en-US", "pt-BR", "es-ES", "ru-RU");
    private final GeneralSettings generalSettings;
    private final MessageSettings messageSettings;
    private final ConfigManager configManager;
    private final PluginServices pluginServices;

    public LangSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageSettings = container.getMessageSettings();
        this.generalSettings = container.getGeneralSettings();
        this.configManager = container.getConfigManager();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, messageSettings.getFeedbackErrorNoPermission());
            return;
        }

        if (args.length == 2) {
            if (AVAILABLE_LANGUAGES.contains(args[1])) {
                String currentLang = generalSettings.getLanguage();
                if (currentLang.equals(args[1])) {
                    sender.sendMessage(messageSettings.getFeedbackErrorLanguageInUse());
                    return;
                }

                configManager.setValue("language", args[1]);
                pluginServices.reloadFiles();
                sender.sendMessage(messageSettings.getFeedbackLangChanged().replace("{0}", args[1]));
            } else {
                sender.sendMessage(messageSettings.getFeedbackIncorrectCommand().replace("{0}", "/dnp lang <" + String.join("/", AVAILABLE_LANGUAGES) + ">"));
            }
        } else {
            sender.sendMessage(messageSettings.getFeedbackIncorrectCommand().replace("{0}", "/dnp lang <lang>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            return Collections.emptyList();
        }

        if (args.size() == 1) {

            return filterContains(AVAILABLE_LANGUAGES, args.get(0));

        }

        return Collections.emptyList();
    }

}
