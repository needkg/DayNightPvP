package org.callv2.daynightpvp.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.callv2.daynightpvp.commands.ISubCommand;
import org.callv2.daynightpvp.di.DependencyContainer;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.services.PluginServices;
import org.callv2.daynightpvp.utils.PlayerUtils;

public class ReloadSubCommand implements ISubCommand {

    private final LangFile langFile;
    private final PluginServices pluginServices;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.langFile = container.getLangFile();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, langFile.getFeedbackError());
            return;
        }

        pluginServices.reloadPlugin();
        PlayerUtils.sendMessage(sender, langFile.getFeedbackReloadPlugin());
    }

}
