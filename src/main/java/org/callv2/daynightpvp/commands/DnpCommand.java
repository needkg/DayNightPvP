package org.callv2.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.callv2.daynightpvp.commands.subcommands.AddWorldSubCommand;
import org.callv2.daynightpvp.commands.subcommands.DelWorldSubCommand;
import org.callv2.daynightpvp.commands.subcommands.ReloadSubCommand;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.services.PluginServices;
import org.callv2.daynightpvp.utils.PlayerUtils;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class DnpCommand implements CommandExecutor, TabCompleter {

    private final LangFile langFile;
    private final ConfigFile configFile;
    private final RunnableHandler runnableHandler;
    private final LoseMoneyOnDeath loseMoneyOnDeath;

    private final Map<String, ISubCommand> subCommands = new HashMap<>();

    public DnpCommand(LangFile langFile, ConfigFile configFile, RunnableHandler runnableHandler, LoseMoneyOnDeath loseMoneyOnDeath) {
        this.langFile = langFile;
        this.configFile = configFile;
        this.runnableHandler = runnableHandler;
        this.loseMoneyOnDeath = loseMoneyOnDeath;

        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.put("reload", new ReloadSubCommand(langFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
        subCommands.put("addworld", new AddWorldSubCommand(langFile, configFile));
        subCommands.put("delworld", new DelWorldSubCommand(langFile, configFile));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {

        if (args.length == 0) {

            PlayerUtils.sendMessage(sender, "");
            PlayerUtils.sendMessage(sender,"§7§l* §a§lCommands§7:");
            PlayerUtils.sendMessage(sender,"");
            PlayerUtils.sendMessage(sender,"§7§l* §7/§9dnp §8-> §7Show all available commands.");
            PlayerUtils.sendMessage(sender,"§7§l* §7/§9dnp reload §8-> §7Reload the plugin.");
            return true;
        }

        ISubCommand dnpSubCommand = subCommands.get(args[0]);

        if (dnpSubCommand == null) {
            PlayerUtils.sendMessage(sender, langFile.getFeedbackNonExistentCommand());
            return true;
        }

        dnpSubCommand.executeCommand(sender, command, commandLabel, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length < 1) return null;

        if (args.length == 1) {
            return this.subCommands.keySet().stream()
                    .filter(subCmd -> sender.hasPermission("dnp.admin"))
                    .filter(subCmd -> subCmd.toLowerCase().startsWith(args[0].toLowerCase()))  // Use startsWith para filtrar sugestões
                    .sorted()
                    .collect(Collectors.toList());
        }

        String cmdName = this.subCommands.keySet().stream()
                .filter(subCmd -> subCmd.equalsIgnoreCase(args[0]))
                .findFirst().orElse(null);

        if (cmdName == null) return new ArrayList<>();

        if (this.subCommands.containsKey(cmdName)) {
            return this.subCommands.get(cmdName).tabComplete(sender, command, alias, Arrays.asList(args).subList(1, args.length));
        }

        return new ArrayList<>();
    }

}
