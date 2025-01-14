package com.needkg.daynightpvp.commands.subcommands;

import com.needkg.daynightpvp.config.settings.MessageSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.needkg.daynightpvp.commands.ISubCommand;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.services.PluginServices;
import com.needkg.daynightpvp.utils.PlayerUtils;

public class ReloadSubCommand implements ISubCommand {

    private final MessageSettings messageSettings;
    private final PluginServices pluginServices;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageSettings = container.getMessageSettings();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, messageSettings.getFeedbackError());
            return;
        }

        pluginServices.reloadPlugin();
        PlayerUtils.sendMessage(sender, messageSettings.getFeedbackReloadPlugin());
    }

}
