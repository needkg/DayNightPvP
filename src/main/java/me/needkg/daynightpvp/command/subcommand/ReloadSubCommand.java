package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ISubCommand {

    private final PluginService pluginService;
    private final SystemMessages systemMessages;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.pluginService = container.getPluginServices();
        this.systemMessages = container.getMessageContainer().getSystem();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage(systemMessages.getErrorMessage());
            return;
        }

        pluginService.reloadPlugin();
        sender.sendMessage(systemMessages.getReloadSuccessMessage());
    }

}
