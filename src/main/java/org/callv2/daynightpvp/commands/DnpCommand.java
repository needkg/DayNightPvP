package org.callv2.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.callv2.daynightpvp.commands.subcommands.*;
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
        subCommands.put("addworld", new AddWorldSubCommand(langFile, configFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
        subCommands.put("delworld", new DelWorldSubCommand(langFile, configFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
        subCommands.put("editworld", new EditWorldSubCommand(langFile, configFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
        subCommands.put("lang", new LangSubCommand(langFile, configFile, new PluginServices(loseMoneyOnDeath, runnableHandler)));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {

        if (args.length == 0) {

            PlayerUtils.sendMessage(sender, "");
            PlayerUtils.sendMessage(sender, "  §a§lDayNightPvP");
            PlayerUtils.sendMessage(sender, "");
            PlayerUtils.sendMessage(sender, "  §b/dnp §8- §7View all commands");
            PlayerUtils.sendMessage(sender, "  §b/dnp reload §8- §7Reload plugin");
            PlayerUtils.sendMessage(sender, "  §b/dnp addworld §8- §7Add world to config");
            PlayerUtils.sendMessage(sender, "  §b/dnp delworld §8- §7Remove world from config");
            PlayerUtils.sendMessage(sender, "  §b/dnp editworld §8- §7Edit world settings");
            PlayerUtils.sendMessage(sender, "  §b/dnp lang §8- §7Change language");
            PlayerUtils.sendMessage(sender, "");
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
        if (args.length < 1) {
            return new ArrayList<>();
        }

        // Primeiro argumento - subcomandos
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            return this.subCommands.keySet().stream()
                    .filter(subCmd -> sender.hasPermission("dnp.admin"))
                    .filter(subCmd -> {
                        // Primeiro tenta match exato no início
                        if (subCmd.toLowerCase().startsWith(search)) {
                            return true;
                        }
                        // Se não encontrar, procura em qualquer parte
                        return subCmd.toLowerCase().contains(search);
                    })
                    .sorted()
                    .collect(Collectors.toList());
        }

        // Argumentos subsequentes - delega para o subcomando
        String cmdName = args[0].toLowerCase();
        ISubCommand subCommand = this.subCommands.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().equals(cmdName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        if (subCommand != null) {
            List<String> subArgs = Arrays.asList(args).subList(1, args.length);
            List<String> completions = subCommand.tabComplete(sender, command, alias, subArgs);

            // Se não houver sugestões e o subcomando existe, retorna lista vazia em vez de null
            return completions != null ? completions : new ArrayList<>();
        }

        return new ArrayList<>();
    }

}
