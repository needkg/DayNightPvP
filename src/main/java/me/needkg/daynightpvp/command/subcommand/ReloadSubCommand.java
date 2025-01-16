package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ISubCommand {

    private final MessageConfiguration messageConfiguration;
    private final PluginService pluginService;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageConfiguration = container.getMessageSettings();
        this.pluginService = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage(messageConfiguration.getFeedbackError());
            return;
        }

        pluginService.reloadPlugin();
        sender.sendMessage(messageConfiguration.getFeedbackReloadPlugin());
    }

}
