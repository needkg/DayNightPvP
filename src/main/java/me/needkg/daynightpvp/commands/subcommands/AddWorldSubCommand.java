package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.ISubCommand;
import me.needkg.daynightpvp.config.ConfigManager;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.services.PluginServices;
import me.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AddWorldSubCommand implements ISubCommand {

    private final ConfigManager configManager;
    private final MessageSettings messageSettings;
    private final PluginServices pluginServices;

    public AddWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configManager = container.getConfigManager();
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
            if (Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                if (!configManager.hasPath("worlds." + args[1])) {
                    addWorldToConfig(args[1]);
                    pluginServices.reloadPlugin();
                    sender.sendMessage(messageSettings.getFeedbackAddedWorld().replace("{0}", args[1]));
                    return;
                }
                sender.sendMessage(messageSettings.getFeedbackWorldAlreadyExists().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(messageSettings.getFeedbackWorldDoesNotExist().replace("{0}", args[1]));
        } else {
            sender.sendMessage(messageSettings.getFeedbackIncorrectCommand().replace("{0}", "/dnp addworld <worldName>"));
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
        configManager.setValue("worlds." + worldName + ".day-night-duration.enabled", false);
        configManager.setValue("worlds." + worldName + ".day-night-duration.day-duration", 600);
        configManager.setValue("worlds." + worldName + ".day-night-duration.night-duration", 600);
        configManager.setValue("worlds." + worldName + ".automatic-pvp.enabled", true);
        configManager.setValue("worlds." + worldName + ".automatic-pvp.day-end", 12000);
        configManager.setValue("worlds." + worldName + ".boss-bar.time-remaining", false);
        configManager.setValue("worlds." + worldName + ".automatic-difficulty.enabled", false);
        configManager.setValue("worlds." + worldName + ".automatic-difficulty.day", "NORMAL");
        configManager.setValue("worlds." + worldName + ".automatic-difficulty.night", "HARD");
        configManager.setValue("worlds." + worldName + ".pvp-settings.keep-inventory-when-killed-by-player", false);
        configManager.setValue("worlds." + worldName + ".notify-players.chat.enabled", true);
        configManager.setValue("worlds." + worldName + ".notify-players.chat.day-night-starts", true);
        configManager.setValue("worlds." + worldName + ".notify-players.chat.hit-another-player-during-day", true);
        configManager.setValue("worlds." + worldName + ".notify-players.title.enabled", true);
        configManager.setValue("worlds." + worldName + ".notify-players.title.fade-in", 20);
        configManager.setValue("worlds." + worldName + ".notify-players.title.stay", 20);
        configManager.setValue("worlds." + worldName + ".notify-players.title.fade-out", 20);
        configManager.setValue("worlds." + worldName + ".notify-players.sound.enabled", true);
        configManager.setValue("worlds." + worldName + ".notify-players.sound.day.sound", "ENTITY_CHICKEN_AMBIENT");
        configManager.setValue("worlds." + worldName + ".notify-players.sound.day.volume", 1.0);
        configManager.setValue("worlds." + worldName + ".notify-players.sound.night.sound", "ENTITY_GHAST_AMBIENT");
        configManager.setValue("worlds." + worldName + ".notify-players.sound.night.volume", 1.0);
        configManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.enabled", false);
        configManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-at-night", true);
        configManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-in-configured-worlds", true);
        configManager.setValue("worlds." + worldName + ".vault.lose-money-on-death.killer-reward-money", true);
        configManager.setValue("worlds." + worldName + ".grief-prevention.pvp-in-land", false);
        configManager.saveFile();
    }

}
