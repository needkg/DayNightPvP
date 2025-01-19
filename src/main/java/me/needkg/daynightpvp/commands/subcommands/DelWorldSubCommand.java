package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.commands.subcommands.core.ISubCommand;
import me.needkg.daynightpvp.commands.subcommands.validators.ArgsLengthValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.PermissionValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.WorldConfiguredValidator;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.services.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final ConfigurationFile configurationFile;
    private final PluginService pluginService;
    private final List<CommandValidator> validators;

    public DelWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageManager = container.getMessageManager();
        this.globalConfigurationManager = container.getGlobalConfigurationManager();
        this.pluginService = container.getPluginService();
        this.configurationFile = container.getConfigurationFile();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
        this.validators.add(new ArgsLengthValidator(2, "/dnp delworld <worldName>", messageManager));
        this.validators.add(new WorldConfiguredValidator(configurationFile, messageManager, true));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        removeWorldFromConfig(args[1]);
        pluginService.reloadPlugin();
        sender.sendMessage(messageManager.getMessage(MessageType.WORLD_EDITOR_MANAGEMENT_DELETED).replace("{0}", args[1]));
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
            for (String worldName : globalConfigurationManager.getWorldNames()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        configurationFile.setValue("worlds." + worldName, null);
        configurationFile.saveFile();
    }
}
