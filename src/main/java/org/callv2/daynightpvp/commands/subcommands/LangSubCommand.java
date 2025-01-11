package org.callv2.daynightpvp.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.callv2.daynightpvp.commands.ISubCommand;
import org.callv2.daynightpvp.di.DependencyContainer;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.services.PluginServices;
import org.callv2.daynightpvp.utils.PlayerUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LangSubCommand implements ISubCommand {

    private static final List<String> AVAILABLE_LANGUAGES = Arrays.asList("en-US", "pt-BR", "es-ES", "ru-RU");
    private final LangFile langFile;
    private final ConfigFile configFile;
    private final PluginServices pluginServices;

    public LangSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.langFile = container.getLangFile();
        this.configFile = container.getConfigFile();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, langFile.getFeedbackErrorNoPermission());
            return;
        }

        if (args.length == 2) {
            if (AVAILABLE_LANGUAGES.contains(args[1])) {
                String currentLang = configFile.getLanguage();
                if (currentLang.equals(args[1])) {
                    sender.sendMessage(langFile.getFeedbackErrorLanguageInUse());
                    return;
                }

                configFile.setValue("language", args[1]);
                pluginServices.reloadPlugin();
                sender.sendMessage(langFile.getFeedbackLangChanged().replace("{0}", args[1]));
            } else {
                sender.sendMessage(langFile.getFeedbackIncorrectCommand().replace("{0}", "/dnp lang <" + String.join("/", AVAILABLE_LANGUAGES) + ">"));
            }
        } else {
            sender.sendMessage(langFile.getFeedbackIncorrectCommand().replace("{0}", "/dnp lang <lang>"));
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
