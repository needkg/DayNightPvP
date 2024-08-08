package org.callv2.daynightpvp.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.callv2.daynightpvp.commands.ISubCommand;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;

import java.util.ArrayList;
import java.util.List;

public class AddWorldSubCommand implements ISubCommand {

    private final LangFile langFile;
    private final ConfigFile configFile;

    public AddWorldSubCommand(LangFile langFile, ConfigFile configFile) {
        this.langFile = langFile;
        this.configFile = configFile;
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2) {
            if (Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]))) {
                if (!configFile.contains("worlds." + args[1])) {
                    addWorldToConfig(args[1]);
                    sender.sendMessage(langFile.getFeedbackAddedWorld().replace("{0}", args[1]));
                    return;
                }
                sender.sendMessage(langFile.getFeedbackWorldAlreadyExists().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(langFile.getFeedbackWorldDoesNotExist().replace("{0}", args[1]));
        } else {
            sender.sendMessage(langFile.getFeedbackIncorrectCommand().replace("{0}", "/dnp addworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase(); // Obtenha o prefixo digitado
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
        configFile.setValue("worlds." + worldName + ".day-night-duration.enabled", false);
        configFile.setValue("worlds." + worldName + ".day-night-duration.day-duration", 600);
        configFile.setValue("worlds." + worldName + ".day-night-duration.night-duration", 600);
        configFile.setValue("worlds." + worldName + ".automatic-pvp.enabled", true);
        configFile.setValue("worlds." + worldName + ".automatic-pvp.day-end", 12000);
        configFile.setValue("worlds." + worldName + ".automatic-difficulty.enabled", false);
        configFile.setValue("worlds." + worldName + ".automatic-difficulty.day", "NORMAL");
        configFile.setValue("worlds." + worldName + ".automatic-difficulty.night", "HARD");
        configFile.setValue("worlds." + worldName + ".pvp-settings.keep-inventory-when-killed-by-player", false);
        configFile.setValue("worlds." + worldName + ".notify-players.chat.enabled", true);
        configFile.setValue("worlds." + worldName + ".notify-players.chat.day-night-starts", true);
        configFile.setValue("worlds." + worldName + ".notify-players.chat.hit-another-player-during-day", true);
        configFile.setValue("worlds." + worldName + ".notify-players.title.enabled", true);
        configFile.setValue("worlds." + worldName + ".notify-players.title.fade-in", 20);
        configFile.setValue("worlds." + worldName + ".notify-players.title.stay", 20);
        configFile.setValue("worlds." + worldName + ".notify-players.title.fade-out", 20);
        configFile.setValue("worlds." + worldName + ".notify-players.sound.enabled", true);
        configFile.setValue("worlds." + worldName + ".notify-players.sound.day.sound", "ENTITY_CHICKEN_AMBIENT");
        configFile.setValue("worlds." + worldName + ".notify-players.sound.day.volume", 1.0);
        configFile.setValue("worlds." + worldName + ".notify-players.sound.night.sound", "ENTITY_GHAST_AMBIENT");
        configFile.setValue("worlds." + worldName + ".notify-players.sound.night.volume", 1.0);
        configFile.setValue("worlds." + worldName + ".vault.lose-money-on-death.enabled", false);
        configFile.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-at-night", true);
        configFile.setValue("worlds." + worldName + ".vault.lose-money-on-death.only-in-configured-worlds", true);
        configFile.setValue("worlds." + worldName + ".vault.lose-money-on-death.killer-reward-money", true);
        configFile.setValue("worlds." + worldName + ".griefprevention.pvp-in-land", false);
        configFile.saveConfig();
    }

}
