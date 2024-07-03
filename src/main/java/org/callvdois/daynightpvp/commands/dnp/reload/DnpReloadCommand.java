package org.callvdois.daynightpvp.commands.dnp.reload;

import org.bukkit.command.CommandSender;
import org.callvdois.daynightpvp.commands.SubCommandHandler;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.services.PluginServices;

public class DnpReloadCommand extends SubCommandHandler {

    private final LangFile langFile;
    private final PluginServices pluginServices;

    public DnpReloadCommand() {
        langFile = new LangFile();
        pluginServices = new PluginServices();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        pluginServices.reloadPlugin();
        sender.sendMessage(langFile.getFeedbackReloadPlugin());
    }
}
