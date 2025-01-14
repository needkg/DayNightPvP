package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.ISubCommand;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.services.PluginServices;
import me.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
