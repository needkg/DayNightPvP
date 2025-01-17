package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.command.subcommand.validators.PermissionValidator;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadSubCommand implements ISubCommand {

    private final PluginService pluginService;
    private final SystemMessages systemMessages;
    private final List<CommandValidator> validators;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.pluginService = container.getPluginServices();
        this.systemMessages = container.getMessageContainer().getSystem();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", systemMessages));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        pluginService.reloadPlugin();
        sender.sendMessage(systemMessages.getReloadSuccessMessage());
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }
}
