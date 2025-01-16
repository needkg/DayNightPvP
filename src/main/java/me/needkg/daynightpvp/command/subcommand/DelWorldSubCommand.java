package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final ConfigurationManager configurationManager;
    private final WorldConfiguration worldConfiguration;
    private final MessageConfiguration messageConfiguration;
    private final PluginService pluginService;

    public DelWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationManager = container.getConfigManager();
        this.worldConfiguration = container.getWorldSettings();
        this.messageConfiguration = container.getMessageSettings();
        this.pluginService = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage(messageConfiguration.getFeedbackError());
            return;
        }

        if (args.length == 2) {
            if (configurationManager.hasPath("worlds." + args[1])) {
                removeWorldFromConfig(args[1]);
                pluginService.reloadPlugin();
                sender.sendMessage(messageConfiguration.getFeedbackDeletedWorld().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(messageConfiguration.getFeedbackWorldIsNotInSettings().replace("{0}", args[1]));
        } else {
            sender.sendMessage(messageConfiguration.getFeedbackIncorrectCommand().replace("{0}", "/dnp delworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (String worldName : worldConfiguration.getWorldNames()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        if (configurationManager.hasPath("worlds." + worldName)) {
            configurationManager.setValue("worlds." + worldName, null);
            configurationManager.saveFile();
        }
    }

}
