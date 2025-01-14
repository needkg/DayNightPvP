package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.ISubCommand;
import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.services.PluginServices;
import me.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final ConfigManager configManager;
    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;
    private final PluginServices pluginServices;

    public DelWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configManager = container.getConfigManager();
        this.worldSettings = container.getWorldSettings();
        this.messageSettings = container.getMessageSettings();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, messageSettings.getFeedbackError());
            return;
        }

        if (args.length == 2) {
            if (configManager.contains("worlds." + args[1])) {
                removeWorldFromConfig(args[1]);
                pluginServices.reloadPlugin();
                sender.sendMessage(messageSettings.getFeedbackDeletedWorld().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(messageSettings.getFeedbackWorldIsNotInSettings().replace("{0}", args[1]));
        } else {
            sender.sendMessage(messageSettings.getFeedbackIncorrectCommand().replace("{0}", "/dnp delworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (String worldName : worldSettings.getWorldNames()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        if (configManager.contains("worlds." + worldName)) {
            configManager.setValue("worlds." + worldName, null);
            configManager.saveConfig();
        }
    }

}
