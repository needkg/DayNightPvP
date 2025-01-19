package me.needkg.daynightpvp.command.impl.subcommand.system;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.validator.permission.PermissionValidator;
import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand implements SubCommand {

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
        sender.sendMessage(messageManager.getMessage(Message.SYSTEM_RELOAD_SUCCESS));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }
} 