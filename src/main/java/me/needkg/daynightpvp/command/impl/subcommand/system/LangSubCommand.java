package me.needkg.daynightpvp.command.impl.subcommand.system;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.validator.language.LanguageValidator;
import me.needkg.daynightpvp.command.validator.permission.PermissionValidator;
import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LangSubCommand implements SubCommand {

    private static final List<String> AVAILABLE_LANGUAGES = Arrays.asList("en-US", "pt-BR", "es-ES", "ru-RU");
    private final MessageManager messageManager;
    private final ConfigurationFile configurationFile;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final PluginService pluginService;
    private final List<CommandValidator> validators;

    public LangSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageManager = container.getMessageManager();
        this.configurationFile = container.getConfigurationFile();
        this.globalConfigurationManager = container.getGlobalConfigurationManager();
        this.pluginService = container.getPluginService();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
        this.validators.add(new LanguageValidator(AVAILABLE_LANGUAGES, messageManager));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String newLang = args[1];
        String currentLang = globalConfigurationManager.getLanguage();

        if (currentLang.equals(newLang)) {
            sender.sendMessage(messageManager.getMessage(Message.SYSTEM_LANGUAGE_ALREADY_IN_USE));
            return;
        }

        configurationFile.setValue("language", newLang);
        pluginService.reloadFiles();
        sender.sendMessage(messageManager.getMessage(Message.SYSTEM_LANGUAGE_CHANGED).replace("{0}", newLang));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        if (!sender.hasPermission("dnp.admin")) {
            return Collections.emptyList();
        }

        if (args.size() == 1) {
            return filterContains(AVAILABLE_LANGUAGES, args.get(0));
        }

        return Collections.emptyList();
    }
} 