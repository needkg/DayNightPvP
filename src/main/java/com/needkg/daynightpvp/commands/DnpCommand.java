package com.needkg.daynightpvp.commands;

import com.needkg.daynightpvp.commands.subcommands.*;
import com.needkg.daynightpvp.config.settings.MessageSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.utils.PlayerUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class DnpCommand implements CommandExecutor, TabCompleter {

    private final MessageSettings messageSettings;
    private final Map<String, ISubCommand> subCommands = new HashMap<>();

    public DnpCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageSettings = container.getMessageSettings();
        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.put("reload", new ReloadSubCommand());
        subCommands.put("addworld", new AddWorldSubCommand());
        subCommands.put("delworld", new DelWorldSubCommand());
        subCommands.put("editworld", new EditWorldSubCommand());
        subCommands.put("lang", new LangSubCommand());
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
            PlayerUtils.sendMessage(sender, messageSettings.getFeedbackNonExistentCommand());
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
