package org.callv2.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.callv2.daynightpvp.commands.subcommands.ReloadSubCommand;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.services.PluginServices;
import org.callv2.daynightpvp.utils.PlayerUtils;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class DnpCommand implements CommandExecutor, TabCompleter {

    private final LangFile langFile;
    private final LoseMoneyOnDeath loseMoneyOnDeath;
    private final RunnableHandler runnableHandler;

    private final Map<String, ISubCommand> subCommands = new HashMap<>();

    public DnpCommand(LangFile langFile, LoseMoneyOnDeath loseMoneyOnDeath, RunnableHandler runnableHandler) {
        this.langFile = langFile;
        this.loseMoneyOnDeath = loseMoneyOnDeath;
        this.runnableHandler = runnableHandler;

        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.put("reload", new ReloadSubCommand(langFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
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
                    .filter(subCmd -> subCmd.contains(args[0]) || subCmd.equalsIgnoreCase(args[0]))
                    .sorted()
                    .collect(toList());
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
