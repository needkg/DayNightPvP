package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.commands.subcommands.core.ISubCommand;
import me.needkg.daynightpvp.commands.subcommands.validators.PermissionValidator;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.services.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand implements ISubCommand {

    private final PluginService pluginService;
    private final MessageManager messageManager;
    private final List<CommandValidator> validators;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.pluginService = container.getPluginService();
        this.messageManager = container.getMessageManager();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        pluginService.reloadPlugin();
        sender.sendMessage(messageManager.getMessage(MessageType.SYSTEM_RELOAD_SUCCESS));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }
}
