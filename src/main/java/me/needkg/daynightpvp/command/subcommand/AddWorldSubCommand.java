package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AddWorldSubCommand implements ISubCommand {

    private final ConfigurationManager configurationManager;
    private final MessageConfiguration messageConfiguration;
    private final PluginService pluginService;

    public AddWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationManager = container.getConfigManager();
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
            if (Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                if (!configurationManager.hasPath("worlds." + args[1])) {
                    addWorldToConfig(args[1]);
                    pluginService.reloadPlugin();
                    sender.sendMessage(messageConfiguration.getFeedbackAddedWorld().replace("{0}", args[1]));
                    return;
                }
                sender.sendMessage(messageConfiguration.getFeedbackWorldAlreadyExists().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(messageConfiguration.getFeedbackWorldDoesNotExist().replace("{0}", args[1]));
        } else {
            sender.sendMessage(messageConfiguration.getFeedbackIncorrectCommand().replace("{0}", "/dnp addworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (World world : Bukkit.getWorlds()) {
                if (World.Environment.NORMAL == world.getEnvironment()) {
                    String worldName = world.getName().toLowerCase();
                    if (worldName.startsWith(prefix)) {
                        suggestions.add(world.getName());
                    }
                }
            }
        }
        return suggestions;
    }

    private void addWorldToConfig(String worldName) {
        configurationManager.setValue("worlds." + worldName + ".day-night-duration.enabled", false);
        configurationManager.setValue("worlds." + worldName + ".day-night-duration.day-duration", 600);
        configurationManager.setValue("worlds." + worldName + ".day-night-duration.night-duration", 600);
        configurationManager.setValue("worlds." + worldName + ".automatic-pvp.enabled", true);
        configurationManager.setValue("worlds." + worldName + ".automatic-pvp.day-end", 12000);
        configurationManager.setValue("worlds." + worldName + ".boss-bar.time-remaining", false);
        configurationManager.setValue("worlds." + worldName + ".automatic-difficulty.enabled", false);
        configurationManager.setValue("worlds." + worldName + ".automatic-difficulty.day", "NORMAL");
        configurationManager.setValue("worlds." + worldName + ".automatic-difficulty.night", "HARD");
        configurationManager.setValue("worlds." + worldName + ".pvp-settings.keep-inventory-when-killed-by-player", false);
        configurationManager.setValue("worlds." + worldName + ".notify-players.chat.enabled", true);
        configurationManager.setValue("worlds." + worldName + ".notify-players.chat.day-night-starts", true);
        configurationManager.setValue("worlds." + worldName + ".notify-players.chat.hit-another-player-during-day", true);
        configurationManager.setValue("worlds." + worldName + ".notify-players.title.enabled", true);
        configurationManager.setValue("worlds." + worldName + ".notify-players.title.fade-in", 20);
        configurationManager.setValue("worlds." + worldName + ".notify-players.title.stay", 20);
        configurationManager.setValue("worlds." + worldName + ".notify-players.title.fade-out", 20);
        configurationManager.setValue("worlds." + worldName + ".notify-players.sound.enabled", true);
        configurationManager.setValue("worlds." + worldName + ".notify-players.sound.day.sound", "ENTITY_CHICKEN_AMBIENT");
        configurationManager.setValue("worlds." + worldName + ".notify-players.sound.day.volume", 1.0);
        configurationManager.setValue("worlds." + worldName + ".notify-players.sound.night.sound", "ENTITY_GHAST_AMBIENT");
        configurationManager.setValue("worlds." + worldName + ".notify-players.sound.night.volume", 1.0);
        configurationManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.enabled", false);
        configurationManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-at-night", true);
        configurationManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-in-configured-worlds", true);
        configurationManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.killer-reward-money", true);
        configurationManager.setValue("worlds." + worldName + ".grief-prevention.pvp-in-land", false);
        configurationManager.saveFile();
    }

}
