package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.subcommands.base.CommandValidator;
import me.needkg.daynightpvp.commands.subcommands.base.ISubCommand;
import me.needkg.daynightpvp.commands.subcommands.validators.LanguageValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.PermissionValidator;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.services.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LangSubCommand implements ISubCommand {

    private static final List<String> AVAILABLE_LANGUAGES = Arrays.asList("en-US", "pt-BR", "es-ES", "ru-RU");
    private final GeneralConfiguration generalConfiguration;
    private final ConfigurationManager configurationManager;
    private final PluginService pluginService;
    private final SystemMessages systemMessages;
    private final List<CommandValidator> validators;

    public LangSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.generalConfiguration = container.getConfigurationContainer().getGeneralConfiguration();
        this.configurationManager = container.getConfigManager();
        this.pluginService = container.getPluginServices();
        this.systemMessages = container.getMessageContainer().getSystem();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", systemMessages));
        this.validators.add(new LanguageValidator(AVAILABLE_LANGUAGES, systemMessages));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String newLang = args[1];
        String currentLang = generalConfiguration.getLanguage();

        if (currentLang.equals(newLang)) {
            sender.sendMessage(systemMessages.getLanguageAlreadyInUseMessage());
            return;
        }

        configurationManager.setValue("language", newLang);
        pluginService.reloadFiles();
        sender.sendMessage(systemMessages.getLanguageChangedMessage().replace("{0}", newLang));
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
