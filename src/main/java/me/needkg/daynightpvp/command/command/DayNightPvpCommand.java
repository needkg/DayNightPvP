package me.needkg.daynightpvp.command.command;

import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.subcommand.system.LangSubCommand;
import me.needkg.daynightpvp.command.subcommand.system.ReloadSubCommand;
import me.needkg.daynightpvp.command.subcommand.world.AddWorldSubCommand;
import me.needkg.daynightpvp.command.subcommand.world.DelWorldSubCommand;
import me.needkg.daynightpvp.command.subcommand.world.EditWorldSubCommand;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class DayNightPvpCommand implements CommandExecutor, TabCompleter {

    private final MessageManager messageManager;
    private final PluginService pluginService;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final ConfigurationFile configurationFile;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public DayNightPvpCommand(MessageManager messageManager, PluginService pluginService, GlobalConfigurationManager globalConfigurationManager, ConfigurationFile configurationFile) {
        this.messageManager = messageManager;
        this.pluginService = pluginService;
        this.globalConfigurationManager = globalConfigurationManager;
        this.configurationFile = configurationFile;
        initializeSubCommands();
    }

    private void initializeSubCommands() {
        subCommands.put("reload", new ReloadSubCommand(pluginService, messageManager));
        subCommands.put("addworld", new AddWorldSubCommand(configurationFile, messageManager, pluginService, globalConfigurationManager));
        subCommands.put("delworld", new DelWorldSubCommand( messageManager,  globalConfigurationManager,  configurationFile,  pluginService));
        subCommands.put("editworld", new EditWorldSubCommand( configurationFile,  messageManager,  globalConfigurationManager,  pluginService));
        subCommands.put("lang", new LangSubCommand(messageManager, configurationFile, globalConfigurationManager, pluginService));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("");
            sender.sendMessage("  §a§lDayNightPvP");
            sender.sendMessage("");
            sender.sendMessage("  §b/dnp §8- §7View all commands");
            sender.sendMessage("  §b/dnp reload §8- §7Reload plugin");
            sender.sendMessage("  §b/dnp addworld §8- §7Add world to config");
            sender.sendMessage("  §b/dnp delworld §8- §7Remove world from config");
            sender.sendMessage("  §b/dnp editworld §8- §7Edit world settings");
            sender.sendMessage("  §b/dnp lang §8- §7Change language");
            sender.sendMessage("");
            return true;
        }

        SubCommand dnpSubCommand = subCommands.get(args[0]);

        if (dnpSubCommand == null) {
            sender.sendMessage(messageManager.getMessage(Message.SYSTEM_COMMAND_NOT_FOUND));
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
        SubCommand subCommand = this.subCommands.entrySet().stream()
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