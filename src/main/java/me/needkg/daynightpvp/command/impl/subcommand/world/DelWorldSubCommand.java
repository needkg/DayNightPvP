package me.needkg.daynightpvp.command.impl.subcommand.world;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.validator.args.ArgsLengthValidator;
import me.needkg.daynightpvp.command.validator.permission.PermissionValidator;
import me.needkg.daynightpvp.command.validator.world.WorldConfiguredValidator;
import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DelWorldSubCommand implements SubCommand {

    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final ConfigurationFile configurationFile;
    private final PluginService pluginService;
    private final List<CommandValidator> validators;

    public DelWorldSubCommand(MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager, ConfigurationFile configurationFile, PluginService pluginService) {
        this.messageManager = messageManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.pluginService = pluginService;
        this.configurationFile = configurationFile;

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
        this.validators.add(new ArgsLengthValidator(2, "/dnp delworld <worldName>", messageManager));
        this.validators.add(new WorldConfiguredValidator(configurationFile, messageManager, true));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        removeWorldFromConfig(args[1]);
        pluginService.reloadPlugin();
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_MANAGEMENT_DELETED).replace("{0}", args[1]));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {

        if (args.size() == 1) {
            return globalConfigurationManager.getEnabledWorlds().stream()
                    .map(World::getName)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void removeWorldFromConfig(String worldName) {
        configurationFile.setValue("worlds." + worldName, null);
        configurationFile.saveFile();
    }
} 