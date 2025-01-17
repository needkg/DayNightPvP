package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.command.subcommand.validators.ArgsLengthValidator;
import me.needkg.daynightpvp.command.subcommand.validators.PermissionValidator;
import me.needkg.daynightpvp.command.subcommand.validators.WorldConfiguredValidator;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.configuration.message.WorldEditorMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final ConfigurationManager configurationManager;
    private final GeneralConfiguration generalConfiguration;
    private final PluginService pluginService;
    private final WorldEditorMessages worldEditorMessages;
    private final SystemMessages systemMessages;
    private final List<CommandValidator> validators;

    public DelWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationManager = container.getConfigManager();
        this.generalConfiguration = container.getConfigurationContainer().getGeneralConfiguration();
        this.worldEditorMessages = container.getMessageContainer().getWorldEditor();
        this.systemMessages = container.getMessageContainer().getSystem();
        this.pluginService = container.getPluginServices();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", systemMessages));
        this.validators.add(new ArgsLengthValidator(2, "/dnp delworld <worldName>", systemMessages));
        this.validators.add(new WorldConfiguredValidator(configurationManager, worldEditorMessages, true));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        removeWorldFromConfig(args[1]);
        pluginService.reloadPlugin();
        sender.sendMessage(worldEditorMessages.getWorldDeletedMessage().replace("{0}", args[1]));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (String worldName : generalConfiguration.getWorldNames()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        configurationManager.setValue("worlds." + worldName, null);
        configurationManager.saveFile();
    }
}
