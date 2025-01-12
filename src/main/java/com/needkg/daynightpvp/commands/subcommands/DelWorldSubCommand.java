package com.needkg.daynightpvp.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.needkg.daynightpvp.commands.ISubCommand;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.files.ConfigFile;
import com.needkg.daynightpvp.files.LangFile;
import com.needkg.daynightpvp.services.PluginServices;
import com.needkg.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final LangFile langFile;
    private final ConfigFile configFile;
    private final PluginServices pluginServices;

    public DelWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.langFile = container.getLangFile();
        this.configFile = container.getConfigFile();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, langFile.getFeedbackError());
            return;
        }

        if (args.length == 2) {
            if (configFile.contains("worlds." + args[1])) {
                removeWorldFromConfig(args[1]);
                pluginServices.reloadPlugin();
                sender.sendMessage(langFile.getFeedbackDeletedWorld().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(langFile.getFeedbackWorldIsNotInSettings().replace("{0}", args[1]));
        } else {
            sender.sendMessage(langFile.getFeedbackIncorrectCommand().replace("{0}", "/dnp delworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (String worldName : configFile.getWorldNames()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        if (configFile.contains("worlds." + worldName)) {
            configFile.setValue("worlds." + worldName, null);
            configFile.saveConfig();
        }
    }

}
